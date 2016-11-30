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

import com.rollsoftware.br.accountmanager.db.entity.LoginInfo;
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
public class LoginInfoServiceTest extends ObjectDataServiceTest {

    public LoginInfoServiceTest(EntityManagerInterface emInterface) {
        super(emInterface);
    }

    @Override
    public <T extends ObjectInterface> T load(Object id) {
        return (T) load(LoginInfo.class, id);
    }

    @Override
    public <T extends ObjectInterface> T load(EntityManager em, Object id) {
        return (T) load(em, LoginInfo.class, id);
    }

    @Override
    protected ObjectInterface createObjectInterface() {
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setUUID("uuid" + Math.random());
        loginInfo.setType("type");

        loginInfo.setUser("user" + Math.random());
        loginInfo.setPass("pass" + Math.random());
        loginInfo.setFirstName("firstName" + Math.random());
        loginInfo.setLastName("lastName" + Math.random());

        return loginInfo;
    }

    @Override
    protected <T extends AbstractServiceFacade>
            T createServiceFacade(EntityManager em) {
        return (T) new LoginInfoService(em);
    }

    @BeforeClass
    public static void setUpClass() {
        ObjectDataServiceTest.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() {
        ObjectDataServiceTest.setUpClass();
    }

    @Before
    @Override
    public void setUp() throws SQLException {
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws SQLException {
        super.tearDown();
    }
}
