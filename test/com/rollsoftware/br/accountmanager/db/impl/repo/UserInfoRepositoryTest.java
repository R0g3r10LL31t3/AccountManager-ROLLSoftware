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

import com.rollsoftware.br.accountmanager.db.impl.entity.LoginInfo;
import com.rollsoftware.br.accountmanager.db.impl.entity.UserInfo;
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
public class UserInfoRepositoryTest extends RepositoryTest {

    private Object entityPK;
    private ObjectInterface entity;

    public UserInfoRepositoryTest(EntityManagerInterface emInterface) {
        super(emInterface);
    }

    @Override
    public EmbeddedAbstractRepository getNewInstance() {
        return new UserInfoRepository();
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
        return (Class<T>) UserInfo.class;
    }

    @Override
    public <T extends ObjectInterface> T getEntity() {
        return (T) entity;
    }

    @Override
    public <T extends ObjectInterface> T createEntity() {
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.getUserInfo().setFirstName("firstName" + Math.random());
        loginInfo.getUserInfo().setLastName("lastName" + +Math.random());

        loginInfo.setUser("user" + Math.random());
        loginInfo.setPass("pass" + +Math.random());
        loginInfo.encryptPass();
        loginInfo.generateUUID();

        return (T) loginInfo.getUserInfo();
    }

    public <T extends ObjectInterface>
            void saveEmbedded(T objectInterface) {
        save(((UserInfo) objectInterface).getLoginInfo());
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
