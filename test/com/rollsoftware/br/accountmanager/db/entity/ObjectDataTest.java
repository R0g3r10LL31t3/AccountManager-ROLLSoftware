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
package com.rollsoftware.br.accountmanager.db.entity;

import com.rollsoftware.br.accountmanager.properties.Resource;
import java.sql.DriverManager;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
public class ObjectDataTest {

    protected static final String PU
            = Resource.getProperty("roll.software.br.application.database.PU");
    protected static final Properties DB_PROPS
            = Resource.getDatabaseProperties();

    protected static EntityManagerFactory EMF;
    protected static EntityManager EM;

    private Integer objectDataPK;

    public ObjectDataTest() {
    }

    protected Integer getObjectDataPK() {
        return objectDataPK;
    }

    protected <T extends ObjectData>
            Class<T> getObjectDataClass() {
        return (Class<T>) ObjectData.class;
    }

    protected ObjectData createObjectData() {
        ObjectData objectData = new ObjectData();
        objectData.setHash("unknown" + Math.random());
        objectData.setType("unknown");
        objectData.generateHash();
        return objectData;
    }

    public void save(ObjectData objectData) {
        EM.getTransaction().begin();

        EM.createNativeQuery("set schema ACCOUNT_MANAGER_DB_APP");

        EM.persist(objectData);
        EM.flush();

        EM.getTransaction().commit();
    }

    public <T extends ObjectData>
            T load() {
        ObjectData objectData
                = EM.find(getObjectDataClass(), getObjectDataPK());
        EM.refresh(objectData);
        return (T) objectData;
    }

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
    public void setUp() throws Exception {

        try {

            ObjectData objectData = createObjectData();

            save(objectData);

            objectDataPK = objectData.getId();
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
            throw ex;
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBasic() {
        System.out.println("Test Basic");

        ObjectData objectData = load();

        System.out.println("Object Data: " + objectData);
    }
}
