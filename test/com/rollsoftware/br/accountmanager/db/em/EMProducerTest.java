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
package com.rollsoftware.br.accountmanager.db.em;

import com.rollsoftware.br.test.util.CDITest;
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
public abstract class EMProducerTest extends CDITest {

    public EMProducerTest() {
    }

    protected abstract void addContextCDI();

    protected abstract <T extends EMProducer>
            Class<T> getEMProducerClass();

    @BeforeClass
    public static void setUpClass() {
        CDITest.setUpClass();
    }

    @AfterClass
    public static void tearDownClass() {
        CDITest.tearDownClass();
    }

    @Before
    @Override
    public void setUp() {
        addContextCDI();
        super.setUp();
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }

    /**
     * Test of class EMProducer.
     */
    @Test
    public void testEMProducer_notNull() {
        System.out.println("EMProducer_notNull");

        EMProducer instance = getManagedBean(getEMProducerClass());

        assertNotNull(instance);
    }

    /**
     * Test of class EMProducer.
     */
    @Test
    public void testEMProducer_emNotNull() {
        System.out.println("EMFProducer_emNotNull");

        EMProducer instance = getManagedBean(getEMProducerClass());

        assertNotNull(instance.createEntityManager());
    }
}