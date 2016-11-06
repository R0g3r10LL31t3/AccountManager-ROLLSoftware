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
import com.rollsoftware.br.accountmanager.db.entity.TokenInfo;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
public class TokenInfoServiceTest extends ObjectDataServiceTest {

    private Object loginInfoPKSaved;

    public TokenInfoServiceTest() {
    }

    private Object saveLoginInfo() {

        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setHash("unknown" + Math.random());
        loginInfo.setType("unknown");
        loginInfo.setUser("unknown" + Math.random());
        loginInfo.setPass("unknown" + Math.random());
        loginInfo.setFirstName("unknown");
        loginInfo.setLastName("unknown");

        loginInfo.generateHash();
        loginInfo.encryptPass();

        EM.getTransaction().begin();

        EM.createNativeQuery("set schema ACCOUNT_MANAGER_DB_APP");

        EM.persist(loginInfo);
        EM.flush();

        EM.getTransaction().commit();

        System.out.println("Save LoginInfo: " + loginInfo.getHash());

        return loginInfo.getHash();
    }

    @Override
    protected ObjectData createObjectData() {

        LoginInfo loginInfo = load(LoginInfo.class, loginInfoPKSaved);

        TokenInfo tokenInfo = new TokenInfo();

        tokenInfo.setHash("unknown" + Math.random());
        tokenInfo.setType("unknown");

        tokenInfo.setAccessToken("unknown" + Math.random());
        tokenInfo.setUserIP("unknown" + Math.random());

        Calendar instance = Calendar.getInstance();

        instance.add(Calendar.MINUTE, 10);

        tokenInfo.setDateCreated(Calendar.getInstance().getTime());
        tokenInfo.setDateAccessed(Calendar.getInstance().getTime());
        tokenInfo.setDateExpires(instance.getTime());

        tokenInfo.setLoginInfo(loginInfo);

        tokenInfo.generateHash();
        tokenInfo.generateToken();

        return tokenInfo;
    }

    @Override
    protected <T extends AbstractServiceFacade>
            T createServiceFacade() {
        return (T) new TokenInfoService(EM);
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
        loginInfoPKSaved = saveLoginInfo();
        super.setUp();
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }
}
