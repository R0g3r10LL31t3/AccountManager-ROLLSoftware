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
public class LoginInfoServiceTest extends ObjectDataServiceTest {

    public LoginInfoServiceTest() {
    }

    @Override
    protected ObjectData createObjectData() {
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setHash("hash" + Math.random());
        loginInfo.setType("type");

        loginInfo.setUser("user" + Math.random());
        loginInfo.setPass("pass" + Math.random());
        loginInfo.setFirstName("firstName" + Math.random());
        loginInfo.setLastName("lastName" + Math.random());

        loginInfo.generateHash();
        loginInfo.encryptPass();

        return loginInfo;
    }

    @Override
    protected <T extends AbstractServiceFacade>
            T createServiceFacade() {
        return (T) new LoginInfoService(EM);
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
    public void setUp() {
        super.setUp();

    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }
}
