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
package com.rollsoftware.br.accountmanager.db.impl.repo;

import com.rollsoftware.br.accountmanager.db.impl.entity.CredentialInfo;
import com.rollsoftware.br.accountmanager.db.impl.entity.LoginInfo;
import com.rollsoftware.br.common.db.entity.ObjectInterface;
import com.rollsoftware.br.common.db.repo.EmbeddedAbstractRepository;
import com.rollsoftware.br.common.db.repo.RepositoryTest;
import com.rollsoftware.br.test.util.EntityManagerInterface;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Rogério
 * @date January, 2017
 */
public class CredentialInfoRepositoryTest extends RepositoryTest {

    private Object entityPK;
    private ObjectInterface entity;

    public CredentialInfoRepositoryTest(EntityManagerInterface emInterface) {
        super(emInterface);
    }

    @Override
    public EmbeddedAbstractRepository getNewInstance() {
        return new CredentialInfoRepository();
    }

    @Override
    public Object getEntityPK() {
        return entityPK;
    }

    @Override
    public Object getEntityPK_NotFound() {
        return "";
    }

    @Override
    public <T extends ObjectInterface> Class<T> getEntityClass() {
        return (Class<T>) CredentialInfo.class;
    }

    @Override
    public <T extends ObjectInterface> T getEntity() {
        return (T) entity;
    }

    @Override
    public <T extends ObjectInterface> T createEntity() {
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.getCredentialInfo().setUser("user" + Math.random());
        loginInfo.getCredentialInfo().setPass("pass" + +Math.random());
        loginInfo.getCredentialInfo().encryptPass();

        loginInfo.setFirstName("firstName" + Math.random());
        loginInfo.setLastName("lastName" + +Math.random());

        loginInfo.generateUUID();

        return (T) loginInfo.getCredentialInfo();
    }

    public <T extends ObjectInterface>
            void saveEmbedded(T objectInterface) {
        save(((CredentialInfo) objectInterface).getLoginInfo());
    }

    @BeforeClass
    public static void setUpClass() {
        RepositoryTest.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() {
        RepositoryTest.tearDownClass();
    }

    @Before
    @Override
    public void setUp() throws SQLException {
        try {
            super.setUp();
            entity = createEntity();

            saveEmbedded(entity);

            entityPK = entity.getUUID();
        } catch (Throwable ex) {
            throw ex;
        }
    }

    @After
    @Override
    public void tearDown() throws SQLException {
        super.tearDown();
    }
}
