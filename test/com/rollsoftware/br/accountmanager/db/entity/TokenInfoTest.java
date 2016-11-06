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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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

    private Object loginInfoPKSaved;

    public TokenInfoTest() {
    }

    private Object saveLoginInfo() {

        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setHash("hash" + Math.random());
        loginInfo.setType("type");

        loginInfo.setUser("user" + Math.random());
        loginInfo.setPass("pass" + Math.random());
        loginInfo.setFirstName("firstName" + Math.random());
        loginInfo.setLastName("lastName" + Math.random());

        loginInfo.generateHash();
        loginInfo.encryptPass();

        save(loginInfo);

        System.out.println("Save LoginInfo: " + loginInfo.getHash());

        return loginInfo.getHash();
    }

    @Override
    protected <T extends ObjectData>
            Class<T> getObjectDataClass() {
        return (Class<T>) TokenInfo.class;
    }

    @Override
    protected ObjectData createObjectData() {

        LoginInfo loginInfo = load(LoginInfo.class, loginInfoPKSaved);

        TokenInfo tokeInfo = new TokenInfo();
        tokeInfo.setHash("hash" + Math.random());
        tokeInfo.setType("type");

        tokeInfo.setAccessToken("accessToken" + Math.random());
        tokeInfo.setUserIP("userIP" + Math.random());

        Calendar instance = Calendar.getInstance();

        instance.add(Calendar.MINUTE, 10);

        tokeInfo.setDateCreated(Calendar.getInstance().getTime());
        tokeInfo.setDateAccessed(Calendar.getInstance().getTime());
        tokeInfo.setDateExpires(instance.getTime());

        tokeInfo.setLoginInfo(loginInfo);

        tokeInfo.generateHash();
        tokeInfo.generateToken();

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
        System.out.println("testBasicTokenInfo");

        TokenInfo tokenInfo = load();

        System.out.println("Token Info: " + tokenInfo);
        System.out.println("Login Info: " + tokenInfo.getLoginInfo());
    }

    @Test
    public void testTokenInfoToXML() throws JAXBException {

        System.out.println("testTokenInfoToXML");

        JAXBContext jc = JAXBContext.newInstance(TokenInfo.class);

        TokenInfo tokenInfo = load();

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        System.out.println("XML Output:");
        marshaller.marshal(tokenInfo, System.out);
    }
}
