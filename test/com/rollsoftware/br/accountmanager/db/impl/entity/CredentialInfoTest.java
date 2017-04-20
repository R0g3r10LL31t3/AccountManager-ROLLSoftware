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

import com.rollsoftware.br.common.db.entity.ObjectEmbedded;
import com.rollsoftware.br.common.db.entity.ObjectEmbeddedTest;
import com.rollsoftware.br.common.db.entity.ObjectInterface;
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
 * @date December, 2016
 */
public class CredentialInfoTest extends ObjectEmbeddedTest {

    public CredentialInfoTest() {
    }

    protected <T extends ObjectInterface>
            Class<T> getObjectInterfaceClass() {
        return (Class<T>) CredentialInfo.class;
    }

    @Override
    protected <T extends ObjectEmbedded>
            Class<T> getObjectEmbeddedClass() {
        return (Class<T>) CredentialInfo.class;
    }

    @Override
    protected CredentialInfo createObjectEmbedded() {
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setUUID("uuid" + Math.random());
        loginInfo.setType("type");

        CredentialInfo credentialInfo = new CredentialInfo();

        credentialInfo.setLoginInfo(loginInfo);
        loginInfo.setCredentialInfo(credentialInfo);

        credentialInfo.setUser("user" + Math.random());
        credentialInfo.setPass("pass" + Math.random());

        credentialInfo.generateUUID();
        credentialInfo.encryptPass();

        return credentialInfo;
    }

    @BeforeClass
    public static void setUpClass() {
        ObjectEmbeddedTest.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() {
        ObjectEmbeddedTest.tearDownClass();
    }

    @Before
    @Override
    public void setUp() throws Exception {
    }

    @After
    @Override
    public void tearDown() throws Exception {
    }

    @Test
    public void testEncryptAndDecrypt() {

        System.out.println("testEncryptAndDecrypt");

        for (int i = 0; i < 10000; i++) {
            String pass = "encryptAndDecrypt:" + Math.random();

            LoginInfo loginInfo = new LoginInfo();
            CredentialInfo credentialInfo = new CredentialInfo();

            loginInfo.setUUID("uuid" + Math.random());
            loginInfo.setType("type");

            credentialInfo.setLoginInfo(loginInfo);

            credentialInfo.setPass(pass);
            credentialInfo.generateUUID();
            credentialInfo.encryptPass();
            credentialInfo.decryptPass();

            assertEquals("Pass: " + pass, pass, credentialInfo.getPass());
        }
    }

    @Test
    public void testBasicCredentialInfo() {

        System.out.println("testBasicCredentialInfo");

        CredentialInfo credentialInfo = createObjectEmbedded();

        System.out.println("Credential Info: " + credentialInfo);
        System.out.println("Credential Info UUID: " + credentialInfo.getUUID());

        assertNotNull(credentialInfo);
        assertTrue(credentialInfo.getPass().length() > 0);
    }

    @Test
    public void testCredentialInfoToXML() throws JAXBException {

        System.out.println("testCredentialInfoToXML");

        JAXBContext jc = JAXBContext.newInstance(getObjectEmbeddedClass());

        CredentialInfo credentialInfo = createObjectEmbedded();

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        System.out.println("XML Output:");
        marshaller.marshal(credentialInfo, System.out);
        System.out.println();
    }

    

    @Test
    public void testCredentialInfoToJSON() throws JAXBException {

        System.out.println("testCredentialInfoToJSON");

        JAXBContext jc = JAXBContext.newInstance(getObjectEmbeddedClass());

        CredentialInfo credentialInfo = createObjectEmbedded();

        Marshaller marshaller = jc.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(
                MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

        System.out.println("JSON Output:");
        marshaller.marshal(credentialInfo, System.out);
        System.out.println();
    }
}
