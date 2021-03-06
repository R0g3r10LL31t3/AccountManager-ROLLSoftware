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
package com.rollsoftware.br.accountmanager.db.impl.entity;

import com.rollsoftware.br.common.db.entity.ObjectData;
import com.rollsoftware.br.common.db.entity.ObjectDataTest;
import java.util.Calendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
public class LoginInfoTest extends ObjectDataTest {

    public LoginInfoTest() {
    }

    private Integer saveTokenInfo() {

        TokenInfo tokeInfo = new TokenInfo();

        tokeInfo.setUUID("uuid" + Math.random());
        tokeInfo.setType("type");

        tokeInfo.setAccessToken("accessToken" + Math.random());
        tokeInfo.setUserIP("userIP" + Math.random());

        Calendar instance = Calendar.getInstance();

        instance.add(Calendar.MINUTE, 10);

        tokeInfo.setDateCreated(Calendar.getInstance().getTime());
        tokeInfo.setDateAccessed(Calendar.getInstance().getTime());
        tokeInfo.setDateExpires(instance.getTime());

        tokeInfo.setLoginInfo(load());

        tokeInfo.generateUUID();
        tokeInfo.generateToken();

        save(tokeInfo);

        System.out.println("Save TokenInfo: " + tokeInfo.getId());
        System.out.println("Save TokenInfo: "
                + tokeInfo.getUUID() + " with LoginInfo: "
                + tokeInfo.getLoginInfo().getUUID());

        return tokeInfo.getId();
    }

    @Override
    protected <T extends ObjectData>
            Class<T> getObjectDataClass() {
        return (Class<T>) LoginInfo.class;
    }

    @Override
    protected <T extends ObjectData>
            T createObjectData() {
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setUUID("uuid" + Math.random());
        loginInfo.setType("type");

        loginInfo.setUser("user" + Math.random());
        loginInfo.setPass("pass" + Math.random());
        loginInfo.setFirstName("firstName" + Math.random());
        loginInfo.setLastName("lastName" + Math.random());

        loginInfo.generateUUID();
        loginInfo.encryptPass();

        return (T) loginInfo;
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
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testEncryptAndDecrypt() {

        System.out.println("testEncryptAndDecrypt");

        for (int i = 0; i < 10000; i++) {
            String pass = "encryptAndDecrypt:" + Math.random();

            LoginInfo loginInfo = new LoginInfo();

            loginInfo.setPass(pass);
            loginInfo.generateUUID();
            loginInfo.encryptPass();
            loginInfo.decryptPass();

            assertEquals("Pass: " + pass, pass, loginInfo.getPass());
        }
    }

    @Test
    public void testBasicLoginInfo() {

        System.out.println("testBasicLoginInfo");

        saveTokenInfo();
        LoginInfo loginInfo = load();

        System.out.println("Login Info: " + loginInfo);
        System.out.println("Login Info UUID: " + loginInfo.getUUID());

        System.out.println("Tokens Info Count: " + loginInfo.getTokenInfos().size());
        for (TokenInfo tokenInfo : loginInfo.getTokenInfos()) {
            System.out.println("Tokens Info: " + tokenInfo);
            System.out.println("Tokens Info UUID: " + tokenInfo.getUUID());
        }

        assertEquals(getObjectDataPK(), loginInfo.getUUID());
        assertTrue(loginInfo.getPass().length() > 0);
    }

    @Test
    public void testLoginInfoToXML() throws JAXBException {

        System.out.println("testLoginInfoToXML");

        JAXBContext jc = JAXBContext.newInstance(getObjectDataClass());

        LoginInfo loginInfo = createObjectData();

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        System.out.println("XML Output:");
        marshaller.marshal(loginInfo, System.out);
        System.out.println();
    }

    @Test
    public void testLoginInfoToJSON() throws JAXBException {

        System.out.println("testLoginInfoToJSON");

        JAXBContext jc = JAXBContext.newInstance(getObjectDataClass());

        LoginInfo loginInfo = createObjectData();

        Marshaller marshaller = jc.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(
                MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

        System.out.println("JSON Output:");
        marshaller.marshal(loginInfo, System.out);
        System.out.println();
    }
}
