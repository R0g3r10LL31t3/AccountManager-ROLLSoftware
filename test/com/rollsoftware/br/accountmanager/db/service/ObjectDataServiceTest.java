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
import com.rollsoftware.br.accountmanager.db.entity.ObjectInterface;
import com.rollsoftware.br.test.util.EntityManagerInterface;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
public class ObjectDataServiceTest extends AbstractServiceFacadeTest {

    private AbstractServiceFacade rest;

    private Object objectDataPK;
    private ObjectInterface objectData;

    public ObjectDataServiceTest(EntityManagerInterface emInterface) {
        super(emInterface);
    }

    @Override
    public <T extends ObjectInterface> T load(Object id) {
        return (T) load(ObjectInterface.class, id);
    }

    @Override
    public <T extends ObjectInterface> T load(EntityManager em, Object id) {
        return (T) load(em, ObjectInterface.class, id);
    }

    protected ObjectInterface createObjectInterface() {
        ObjectData _objectData = new ObjectData();

        _objectData.setUUID("uuid" + Math.random());
        _objectData.setType("type");

        return _objectData;
    }

    private <T extends AbstractServiceFacade>
            T createServiceFacade() {
        return createServiceFacade(getEntityManager());
    }

    protected <T extends AbstractServiceFacade>
            T createServiceFacade(EntityManager em) {
        return (T) new ObjectDataService(em);
    }

    @Override
    public AbstractServiceFacade getInstance() {
        return rest;
    }

    @Override
    public AbstractServiceFacade getNewInstance(EntityManager em) {
        return createServiceFacade(em);
    }

    @Override
    public <T extends ObjectInterface> T getEntity() {
        return (T) objectData;
    }

    @Override
    public <T extends ObjectInterface> T createEntity() {
        return (T) createObjectInterface();
    }

    @Override
    public Object getEntityPK() {
        return objectDataPK;
    }

    @Override
    public Object getEntityPK_NotFound() {
        return "";
    }

    @BeforeClass
    public static void setUpClass() {
        AbstractServiceFacadeTest.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() {
        AbstractServiceFacadeTest.tearDownClass();
    }

    @Before
    @Override
    public void setUp() throws SQLException {
        try {
            super.setUp();
            objectData = createObjectInterface();

            save(objectData);

            objectDataPK = objectData.getUUID();

            rest = createServiceFacade();
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
            throw ex;
        }
    }

    @After
    @Override
    public void tearDown() throws SQLException {
        super.tearDown();
    }
}
