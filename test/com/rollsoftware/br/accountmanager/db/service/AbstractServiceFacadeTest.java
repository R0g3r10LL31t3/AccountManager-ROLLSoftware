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

import com.rollsoftware.br.accountmanager.properties.Resource;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
public abstract class AbstractServiceFacadeTest {

    private static final String PU
            = Resource.getProperty("roll.software.br.application.database.PU");
    private static final Properties DB_PROPS
            = Resource.getDatabaseProperties();

    protected EntityManagerFactory emf;
    protected EntityManager em;

    public AbstractServiceFacadeTest() {
    }

    public abstract AbstractServiceFacade getInstance();

    public abstract Object getId();

    public abstract Object getEntity();

    @BeforeClass
    public static void setUpClass() {
        DriverManager.setLogWriter(new java.io.PrintWriter(System.out));
    }

    @AfterClass
    public static void tearDownClass() {
        DriverManager.setLogWriter(null);
    }

    @Before
    public void setUp() {
        try {
            emf = Persistence.createEntityManagerFactory(PU);
            em = emf.createEntityManager(DB_PROPS);

            em.getTransaction().begin();

            em.createNativeQuery("set schema ACCOUNT_MANAGER_DB_APP");

            em.getTransaction().commit();

        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
            throw ex;
        }
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
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
     * @throws java.lang.Exception
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Object entity = getEntity();

        AbstractServiceFacade instance = getInstance();

        instance.create(entity);

        System.out.println("Entity: " + entity);
    }

    /**
     * Test of edit method, of class AbstractFacade.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        Object entity = getEntity();
        AbstractServiceFacade instance = getInstance();

        instance.edit(entity);

        System.out.println("Entity: " + entity);
    }

    /**
     * Test of remove method, of class AbstractFacade.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Object entity = getEntity();
        AbstractServiceFacade instance = getInstance();

        instance.remove(entity);

        System.out.println("Entity: " + entity);
    }

    /**
     * Test of find method, of class AbstractFacade.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        Object id = getId();
        AbstractServiceFacade instance = getInstance();

        Object result = instance.find(id);

        assertNotNull(result);

        System.out.println("Entity: " + result);
    }

    /**
     * Test of findAll method, of class AbstractFacade.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        AbstractServiceFacade instance = getInstance();

        List result = instance.findAll();

        System.out.println("Finded: " + result.size());

        assertNotNull(result);
    }

    /**
     * Test of findRange method, of class AbstractFacade.
     */
    @Test
    public void testFindRange() {
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
     * Test of count method, of class AbstractFacade.
     */
    @Test
    public void testCount() throws Exception {
        System.out.println("count");
        AbstractServiceFacade instance = getInstance();
        int expResult = 0;
        int result = instance.count();

        System.out.println("Counted: " + result);

        assertNotEquals(expResult, result);
    }
}
