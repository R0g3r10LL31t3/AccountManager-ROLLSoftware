/*
 *          Copyright 2016-2026 Rogério Lecarião Leite
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  CEO 2016: Rogério Lecarião Leite; ROLL Software
 */
package com.rollsoftware.br.accountmanager.db.service;

import com.rollsoftware.br.accountmanager.db.app.NotFoundEntityException;
import com.rollsoftware.br.accountmanager.db.entity.ObjectData;
import com.rollsoftware.br.test.util.EntityManagerInterface;
import com.rollsoftware.br.test.util.EntityManagerShared;
import com.rollsoftware.br.test.util.EntityManagerSingle;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
@RunWith(Parameterized.class)
public abstract class AbstractServiceFacadeTest {

    private final EntityManagerInterface emInterface;
    private EntityManager em;

    @Parameterized.Parameters
    public static Collection<Object[]> entityManagerParameters() {
        return Arrays.asList(new Object[][]{
            {new EntityManagerShared()},
            {new EntityManagerSingle()}
        });
    }

    public AbstractServiceFacadeTest(EntityManagerInterface emInterface) {
        this.emInterface = emInterface;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public abstract AbstractServiceFacade getInstance();

    public abstract AbstractServiceFacade getNewInstance(EntityManager em);

    public abstract Object getEntityPK();

    public abstract Object getEntityPK_NotFound();

    public abstract <T extends ObjectData> T getEntity();

    public abstract <T extends ObjectData> T createEntity();

    public <T extends ObjectData>
            void save(T objectData) {
        em.getTransaction().begin();

        em.createNativeQuery("set schema ACCOUNT_MANAGER_DB_APP");

        em.persist(objectData);
        em.flush();

        em.getTransaction().commit();
    }

    public <T extends ObjectData>
            T load(EntityManager em, Class<T> clazz, Object id) {
        ObjectData _objectData
                = em.find(clazz, id);
        em.refresh(_objectData);
        return (T) _objectData;
    }

    public <T extends ObjectData>
            T load(Class<T> clazz, Object id) {
        return (T) load(em, clazz, id);
    }

    public abstract <T extends ObjectData> T load(Object id);

    public abstract <T extends ObjectData> T load(EntityManager em, Object id);

    @BeforeClass
    public static void setUpClass() {
        DriverManager.setLogWriter(new java.io.PrintWriter(System.out));
    }

    @AfterClass
    public static void tearDownClass() {
        DriverManager.setLogWriter(null);
    }

    @Before
    public void setUp() throws SQLException {
        System.out.println("setUp is finishing.");
        emInterface.setUp();
        em = emInterface.getEntityManager();
        System.out.println(emInterface.getDescription());
        System.out.println("setUp is finished.");
    }

    @After
    public void tearDown() throws SQLException {
        System.out.println("tearDown is finishing.");
        emInterface.tearDown();
        System.out.println("tearDown is finished.");
    }

    /**
     * Test of getEntityManager method, of class AbstractFacade.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        AbstractServiceFacade instance = getInstance();
        EntityManager result = instance.getEntityManager();

        assertNotNull(result);
    }

    /**
     * Test of create method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test
    public void testCreate() throws SQLException, Exception {
        System.out.println("create");
        ObjectData entity = createEntity();

        AbstractServiceFacade instance = getInstance();

        instance.create(entity);

        System.out.println("Entity: " + entity);
    }

    /**
     * Test of edit method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit() throws SQLException, Exception {
        System.out.println("edit");
        ObjectData entity = getEntity();
        AbstractServiceFacade instance = getInstance();

        instance.edit(entity);

        System.out.println("Entity: " + entity);
    }

    /**
     * Test of remove method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test
    public void testRemove() throws SQLException, Exception {
        System.out.println("remove");
        ObjectData entity = getEntity();
        AbstractServiceFacade instance = getInstance();

        instance.remove(entity);

        System.out.println("Entity: " + entity);
    }

    /**
     * Test of remove method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test(expected = OptimisticLockException.class)
    public void testRemove_2x() throws SQLException, Exception {
        System.out.println("remove_2x");
        ObjectData entity = getEntity();
        AbstractServiceFacade instance = getInstance();

        instance.remove(entity);
        instance.remove(entity);

        fail("Expected exception.");

        System.out.println("Entity: " + entity);
    }

    /**
     * Test of remove method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveID() throws SQLException, Exception {
        System.out.println("removeID");
        Object id = getEntityPK();
        AbstractServiceFacade instance = getInstance();

        instance.remove(id);

        System.out.println("Entity PK: " + id);
    }

    /**
     * Test of remove method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test(expected = NotFoundEntityException.class)
    public void testRemoveID_NotFound() throws SQLException, Exception {
        System.out.println("removeID_NotFound");
        Object id = getEntityPK_NotFound();
        AbstractServiceFacade instance = getInstance();

        instance.remove(id);

        fail("Expected exception.");

        System.out.println("Entity PK: " + id);
    }

    /**
     * Test of find method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test
    public void testFind() throws SQLException, Exception {
        System.out.println("find");
        Object id = getEntityPK();
        AbstractServiceFacade instance = getInstance();

        Object result = instance.find(id);

        assertNotNull(result);

        System.out.println("Entity: " + result);
    }

    /**
     * Test of find method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test(expected = NotFoundEntityException.class)
    public void testFind_NofFound() throws SQLException, Exception {
        System.out.println("find_NofFound");
        Object id = getEntityPK_NotFound();
        AbstractServiceFacade instance = getInstance();

        Object result = instance.find(id);

        fail("Expected expection");

        System.out.println("Entity: " + result);
    }

    /**
     * Test of findAll method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAll() throws SQLException, Exception {
        System.out.println("findAll");
        AbstractServiceFacade instance = getInstance();

        List result = instance.findAll();

        System.out.println("Finded: " + result.size());

        assertNotNull(result);
    }

    /**
     * Test of findRange method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test
    public void testFindRange() throws SQLException, Exception {
        System.out.println("findRange");
        int from = 0;
        int to = 1;
        AbstractServiceFacade instance = getInstance();

        List expResult = null;
        List result = instance.findRange(from, to);

        assertNotEquals(expResult, result);
        assertFalse(result.isEmpty());

        System.out.println("Entities: " + result.size());
    }

    /**
     * Test of findRange method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test(expected = PersistenceException.class)
    public void testFindRange_from2to1() throws SQLException, Exception {
        System.out.println("findRange");
        int from = 2;
        int to = 1;
        AbstractServiceFacade instance = getInstance();

        List result = instance.findRange(from, to);

        fail("Expected exception.");

        System.out.println("Entities: " + result.size());
    }

    /**
     * Test of count method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    @Test
    public void testCount() throws SQLException, Exception {
        System.out.println("count");
        AbstractServiceFacade instance = getInstance();
        int expResult = 0;
        int result = instance.count();

        System.out.println("Counted: " + result);

        assertNotEquals(expResult, result);
    }

    /**
     * Test of remove method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     */
    @Test
    public void testRemove_inSequential_notThreaded() throws SQLException {
        System.out.println("remove_inSequential_notThreaded");
        int nThreads = 100;

        List<Throwable> sqlExceptions = new ArrayList();
        List<Throwable> exceptions = new ArrayList();

        List<AbstractServiceFacade> instances = new ArrayList();

        System.out.println("EntityPK: " + getEntityPK());

        for (int i = 0; i < nThreads; i++) {
            instances.add(getNewInstance(emInterface.getEntityManager()));
        }

        for (int i = 0; i < nThreads; i++) {
            try {
                ObjectData objectData = instances.get(i).find(getEntityPK());
                instances.get(i).remove(objectData);
            } catch (SQLException | PersistenceException ex) {
                sqlExceptions.add(ex);
            } catch (Throwable ex) {
                exceptions.add(ex);
            }
        }

        System.out.println("SQLException: " + sqlExceptions.size());
        for (Throwable ex : sqlExceptions) {

            if (ex instanceof SQLException) {
                SQLException sex = (SQLException) ex;
                System.out.println(
                        "Message: " + sex.getMessage()
                        + ", State: " + sex.getSQLState()
                        + ", ErrorCode: " + sex.getErrorCode());
            } else {
                System.out.println(
                        "Message: " + ex.getMessage());
            }
        }

        System.out.println("Exception: " + exceptions.size());
        for (Throwable ex : exceptions) {
            System.out.println(
                    "Message: " + ex.getMessage());
        }

        assertTrue("Throwable exceptions.", exceptions.isEmpty());
        assertEquals("SQL exceptions.", nThreads - 1, sqlExceptions.size());
    }

    /**
     * Test of remove method, of class AbstractFacade.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.InterruptedException
     */
    @Test(timeout = 1000)
    public void testRemove_inParallel_threaded()
            throws SQLException, InterruptedException {
        System.out.println("remove_inParallel_threaded");

        int nThreads = 100;

        List<Throwable> sqlExceptions = new ArrayList();
        List<Throwable> exceptions = new ArrayList();
        List<Thread> instances = new ArrayList();

        CountDownLatch cdl = new CountDownLatch(nThreads);

        System.out.println("EntityPK: " + getEntityPK());

        for (int i = 0; i < nThreads; i++) {
            Runnable runnable = () -> {
                AbstractServiceFacade instance = null;
                try {
                    instance = getNewInstance(emInterface.getEntityManager());

                    ObjectData objectData = instance.find(getEntityPK());

                    cdl.countDown();

                    System.out.println("Awaiting: " + cdl.getCount() + "/" + nThreads);
                    cdl.await(100, TimeUnit.MILLISECONDS);

                    System.out.println("Then try remove: "
                            + Thread.currentThread().getName());
                    instance.remove(objectData);
                    System.out.println("Removed: "
                            + Thread.currentThread().getName());
                } catch (SQLException | PersistenceException ex) {
                    System.out.println("Fail to remove: "
                            + Thread.currentThread().getName());
                    sqlExceptions.add(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (Throwable ex) {
                    System.out.println("Fail to remove: "
                            + Thread.currentThread().getName());
                    exceptions.add(ex);
                }
            };

            instances.add(new Thread(runnable, "Service-" + i));
        }

        for (Thread instance : instances) {
            instance.start();
        }

        for (Thread instance : instances) {
            instance.join();
        }

        System.out.println("SQLException: " + sqlExceptions.size());
        for (Throwable ex : sqlExceptions) {
            if (ex instanceof SQLException) {
                SQLException sex = (SQLException) ex;
                System.out.println(
                        "Message: " + sex.getMessage()
                        + ", State: " + sex.getSQLState()
                        + ", ErrorCode: " + sex.getErrorCode());
            } else {
                System.out.println(
                        "Message: " + ex.getMessage());
            }
        }

        System.out.println("Exception: " + exceptions.size());
        for (Throwable ex : exceptions) {
            System.out.println(
                    "Message: " + ex.getMessage());
        }

        assertTrue("Throwable exceptions.", exceptions.isEmpty());
        assertEquals("SQL exceptions.", nThreads - 1, sqlExceptions.size());
    }
}
