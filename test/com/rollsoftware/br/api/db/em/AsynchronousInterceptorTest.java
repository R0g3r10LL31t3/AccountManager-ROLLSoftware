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
package com.rollsoftware.br.api.db.em;

import com.rollsoftware.br.api.db.em.AsynchronousInterceptor;
import com.rollsoftware.br.api.db.em.Asynchronized;
import com.rollsoftware.br.api.concurrent.ServiceThreadPool;
import com.rollsoftware.br.test.util.CDITest;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
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
public class AsynchronousInterceptorTest extends CDITest {

    public AsynchronousInterceptorTest() {
    }

    protected void addContextCDI() {
        WELD.addBeanClass(BeanTest.class);
        WELD.addBeanClass(ThreadPoolTest.class);
        WELD.addInterceptor(getAsynchronousInterceptorClass());
    }

    protected <T extends AsynchronousInterceptor>
            Class<T> getAsynchronousInterceptorClass() {
        return (Class<T>) AsynchronousInterceptor.class;
    }

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
     * Test of class AsynchronousInterceptor.
     */
    @Test
    public void testAsynchronousInterceptor_notNull() {
        System.out.println("AsynchronousInterceptor_notNull");

        AsynchronousInterceptor instance
                = getManagedBean(getAsynchronousInterceptorClass());

        assertNotNull(instance);
    }

    /**
     * Test of class BeanTest.
     */
    @Test
    public void testBeanTest_notNull() {
        System.out.println("BeanTest_notNull");

        BeanTest instance = getManagedBean(BeanTest.class);

        assertNotNull(instance);
        instance.beanMethod();
    }

    public static class BeanTest {

        @Asynchronized
        public void beanMethod() {
            System.out.println("Invoking BeanTest method.");
        }
    }

    public static class ThreadPoolTest {

        @Produces
        @Default
        public ServiceThreadPool getServiceThreadPool() {
            return new ServiceThreadPool() {
                @Override
                public void invokeLater(Runnable runnable) {
                    runnable.run();
                }

                @Override
                public void startup() {
                }

                @Override
                public void shutdown() {
                }
            };
        }
    }

}
