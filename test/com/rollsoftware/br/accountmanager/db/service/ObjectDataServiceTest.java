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
    private ObjectData objectData;

    public ObjectDataServiceTest() {
    }

    public void save(ObjectData objectData) {
        EM.getTransaction().begin();

        EM.createNativeQuery("set schema ACCOUNT_MANAGER_DB_APP");

        EM.persist(objectData);
        EM.flush();

        EM.getTransaction().commit();
    }

    public <T extends ObjectData>
            T load(Class<T> clazz, Object id) {
        ObjectData _objectData
                = EM.find(clazz, id);
        EM.refresh(_objectData);
        return (T) _objectData;
    }

    protected ObjectData createObjectData() {
        ObjectData _objectData = new ObjectData();
        _objectData.setHash("unknown" + Math.random());
        _objectData.setType("unknown");
        _objectData.generateHash();
        return _objectData;
    }

    protected <T extends AbstractServiceFacade>
            T createServiceFacade() {
        return (T) new ObjectDataService(EM);
    }

    @Override
    public AbstractServiceFacade getInstance() {
        return rest;
    }

    @Override
    public Object getEntity() {
        return objectData;
    }

    @Override
    public Object getObjectDataPK() {
        return objectDataPK;
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
    public void setUp() {
        super.setUp();
        try {
            EM.getTransaction().begin();

            EM.createNativeQuery("set schema ACCOUNT_MANAGER_DB_APP");

            objectData = createObjectData();

            EM.persist(objectData);

            EM.getTransaction().commit();

            objectDataPK = objectData.getHash();

            rest = createServiceFacade();
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
            throw ex;
        }
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }
}
