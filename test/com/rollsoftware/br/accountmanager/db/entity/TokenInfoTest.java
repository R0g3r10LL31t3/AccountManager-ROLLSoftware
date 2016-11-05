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

import java.util.Calendar;
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
public class TokenInfoTest extends ObjectDataTest {

    private Integer loginInfoPKSaved;

    public TokenInfoTest() {
    }

    private Integer saveLoginInfo() {

        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setHash("unknown" + Math.random());
        loginInfo.setType("unknown");
        loginInfo.setUser("unknown" + Math.random());
        loginInfo.setPass("unknown" + Math.random());
        loginInfo.setFirstName("unknown");
        loginInfo.setLastName("unknown");

        loginInfo.generateHash();
        loginInfo.encryptPass();

        save(loginInfo);

        System.out.println("Save LoginInfo: " + loginInfo.getHash());

        return loginInfo.getId();
    }

    @Override
    protected <T extends ObjectData>
            Class<T> getObjectDataClass() {
        return (Class<T>) TokenInfo.class;
    }

    @Override
    protected ObjectData createObjectData() {

        Integer loginInfoPK = loginInfoPKSaved;

        TokenInfo tokeInfo = new TokenInfo();
        tokeInfo.setHash("unknown" + Math.random());
        tokeInfo.setType("unknown");

        tokeInfo.setAccessToken("unknown" + Math.random());
        tokeInfo.setUserIP("unknown" + Math.random());

        Calendar instance = Calendar.getInstance();

        instance.add(Calendar.MINUTE, 10);

        tokeInfo.setDateCreated(Calendar.getInstance().getTime());
        tokeInfo.setDateAccessed(Calendar.getInstance().getTime());
        tokeInfo.setDateExpires(instance.getTime());

        tokeInfo.setLoginInfo(new LoginInfo(loginInfoPK));

        tokeInfo.generateToken();
        tokeInfo.generateHash();

        return tokeInfo;
    }

    @BeforeClass
    public static void setUpClass() {
        ObjectDataTest.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() {
        ObjectDataTest.tearDownClass();
    }

    @Before
    @Override
    public void setUp() throws Exception {
        loginInfoPKSaved = saveLoginInfo();
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testBasicTokenInfo() {
        System.out.println("Test Basic Token Info");

        TokenInfo tokenInfo = load();

        System.out.println("Token Info: " + tokenInfo);
        System.out.println("Login Info: " + tokenInfo.getLoginInfo());
    }
}
