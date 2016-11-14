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

import com.rollsoftware.br.accountmanager.db.entity.ObjectData;
import com.rollsoftware.br.accountmanager.properties.Resource;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    protected static EntityManagerFactory EMF;
    protected static EntityManager EM;

    public AbstractServiceFacadeTest() {
    }

    public abstract AbstractServiceFacade getInstance();

    public abstract Object getEntityPK();

    public abstract Object getEntityPK_NotFound();

    public abstract <T extends ObjectData> T getEntity();

    public abstract <T extends ObjectData> T createEntity();

    @BeforeClass
    public static void setUpClass() {
        DriverManager.setLogWriter(new java.io.PrintWriter(System.out));
        EMF = Persistence.createEntityManagerFactory(PU);
        EM = EMF.createEntityManager(DB_PROPS);
    }

    @AfterClass
    public static void tearDownClass() {
        EM.close();
        EMF.close();
        DriverManager.setLogWriter(null);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
     */
    @Test
    public void testCreate() throws SQLException {
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
     */
    @Test
    public void testEdit() throws SQLException {
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
     */
    @Test
    public void testRemove() throws SQLException {
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
     */
    @Test
    public void testRemoveID() throws SQLException {
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
     */
    @Test(expected = NotFoundEntityException.class)
    public void testRemoveID_NotFound() throws SQLException {
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
     */
    @Test
    public void testFind() throws SQLException {
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
     */
    @Test(expected = NotFoundEntityException.class)
    public void testFind_NofFound() throws SQLException {
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
     */
    @Test
    public void testFindAll() throws SQLException {
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
     */
    @Test
    public void testFindRange() throws SQLException {
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
     */
    @Test(expected = javax.persistence.PersistenceException.class)
    public void testFindRange_from2to1() throws SQLException {
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
     */
    @Test
    public void testCount() throws SQLException {
        System.out.println("count");
        AbstractServiceFacade instance = getInstance();
        int expResult = 0;
        int result = instance.count();

        System.out.println("Counted: " + result);

        assertNotEquals(expResult, result);
    }
}
