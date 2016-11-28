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
import com.rollsoftware.br.test.util.EntityManagerInterface;
import java.sql.SQLException;
import java.util.Calendar;
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
public class TokenInfoServiceTest extends ObjectDataServiceTest {

    private Object loginInfoPKSaved;

    public TokenInfoServiceTest(EntityManagerInterface emInterface) {
        super(emInterface);
    }

    @Override
    public <T extends ObjectData> T load(Object id) {
        return (T) load(TokenInfo.class, id);
    }

    @Override
    public <T extends ObjectData> T load(EntityManager em, Object id) {
        return (T) load(em, TokenInfo.class, id);
    }

    private Object saveLoginInfo() {

        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setUUID("uuid" + Math.random());
        loginInfo.setType("type");

        loginInfo.setUser("user" + Math.random());
        loginInfo.setPass("pass" + Math.random());
        loginInfo.setFirstName("firstName" + Math.random());
        loginInfo.setLastName("lastName" + Math.random());

        save(loginInfo);

        System.out.println("Save LoginInfo: " + loginInfo.getUUID());

        return loginInfo.getUUID();
    }

    @Override
    protected ObjectData createObjectData() {

        loginInfoPKSaved = saveLoginInfo();

        LoginInfo loginInfo = load(LoginInfo.class, loginInfoPKSaved);

        TokenInfo tokenInfo = new TokenInfo();

        tokenInfo.setUUID("uuid" + Math.random());
        tokenInfo.setType("type");

        tokenInfo.setAccessToken("accessToken" + Math.random());
        tokenInfo.setUserIP("userIP" + Math.random());

        Calendar instance = Calendar.getInstance();

        instance.add(Calendar.MINUTE, 10);

        tokenInfo.setDateCreated(Calendar.getInstance().getTime());
        tokenInfo.setDateAccessed(Calendar.getInstance().getTime());
        tokenInfo.setDateExpires(instance.getTime());

        tokenInfo.setLoginInfo(loginInfo);

        return tokenInfo;
    }

    @Override
    protected <T extends AbstractServiceFacade>
            T createServiceFacade(EntityManager em) {
        return (T) new TokenInfoService(em);
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
