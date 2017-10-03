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
package com.rollsoftware.br.common.db.em;

import com.rollsoftware.br.common.db.em.AsynchronousInterceptor;
import com.rollsoftware.br.common.db.em.Asynchronized;
import com.rollsoftware.br.common.concurrent.ServiceThreadPool;
import com.rollsoftware.br.test.util.CDITest;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
        //WELD.addExtension(new ExtensionTest());
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

        List<Interceptor<?>> resolveInterceptors
                = weldContainer.getBeanManager().resolveInterceptors(
                        InterceptionType.AROUND_INVOKE,
                        () -> {
                            return Asynchronized.class;
                        }
                //BeanTest.class.getAnnotation(Asynchronized.class)
                );

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

//    /**
//     * Test of intercept method, of class AsynchronousInterceptor.
//     */
//    @Test
//    public void testIntercept() throws Exception {
//        System.out.println("intercept");
//        InvocationContext ctx = null;
//        AsynchronousInterceptor instance = new AsynchronousInterceptor();
//        Object expResult = null;
//        Object result = instance.intercept(ctx);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    //@RequestScoped
    //@Interceptors({AsynchronousInterceptor.class})
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
                public Future invokeLater(Callable callable) {
                    return new Future() {
                        @Override
                        public boolean cancel(boolean mayInterruptIfRunning) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public boolean isCancelled() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public boolean isDone() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public Object get() throws InterruptedException, ExecutionException {
                            Object result = null;
                            try {
                                result = callable.call();
                            } catch (Exception ex) {
                                throw new ExecutionException(ex);
                            }
                            return result;
                        }

                        @Override
                        public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                            Object result = null;
                            try {
                                result = callable.call();
                            } catch (Exception ex) {
                                throw new ExecutionException(ex);
                            }
                            return result;
                        }
                    };
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

//    public static class ExtensionTest implements Extension {
//
//        public <T> void processAnnotatedType(
//                @Observes ProcessAnnotatedType<T> processAnnotatedType) {
//
//            AnnotatedType<T> annotatedType = processAnnotatedType
//                    .getAnnotatedType();
//
//            //if (annotatedType.getJavaClass().equals(TestBean.class)) {
//            Annotation asynchronizedAnnotation = new Annotation() {
//                @Override
//                public Class<? extends Annotation> annotationType() {
//                    return AsynchronizedInterceptation.class;
//                }
//            };
//
//            AnnotatedTypeWrapper<T> wrapper = new AnnotatedTypeWrapper<T>(
//                    annotatedType, annotatedType.getAnnotations());
//            wrapper.addAnnotation(asynchronizedAnnotation);
//
//            processAnnotatedType.setAnnotatedType(wrapper);
//            //}
//        }
//    }
//
//    public static class AnnotatedTypeWrapper<T> implements AnnotatedType<T> {
//
//        private final AnnotatedType<T> wrapped;
//        private final Set<Annotation> annotations;
//
//        public AnnotatedTypeWrapper(AnnotatedType<T> wrapped,
//                Set<Annotation> annotations) {
//            this.wrapped = wrapped;
//            this.annotations = new HashSet<>(annotations);
//        }
//
//        public void addAnnotation(Annotation annotation) {
//            annotations.add(annotation);
//        }
//
//        @Override
//        public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
//            return wrapped.getAnnotation(annotationType);
//        }
//
//        @Override
//        public Set<Annotation> getAnnotations() {
//            return annotations;
//        }
//
//        @Override
//        public Type getBaseType() {
//            return wrapped.getBaseType();
//        }
//
//        @Override
//        public Set<Type> getTypeClosure() {
//            return wrapped.getTypeClosure();
//        }
//
//        @Override
//        public boolean isAnnotationPresent(
//                Class<? extends Annotation> annotationType) {
//            for (Annotation annotation : annotations) {
//                if (annotationType.isInstance(annotation)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        @Override
//        public Set<AnnotatedConstructor<T>> getConstructors() {
//            return wrapped.getConstructors();
//        }
//
//        @Override
//        public Set<AnnotatedField<? super T>> getFields() {
//            return wrapped.getFields();
//        }
//
//        @Override
//        public Class<T> getJavaClass() {
//            return wrapped.getJavaClass();
//        }
//
//        @Override
//        public Set<AnnotatedMethod<? super T>> getMethods() {
//            return wrapped.getMethods();
//        }
//
//    }
//    public static class ExtensionTest implements Extension {
//
//        public void afterDeployment(
//                @Observes AfterDeploymentValidation event,
//                BeanManager beanManager) {
//            setContextActive(beanManager, SessionScoped.class);
//            setContextActive(beanManager, RequestScoped.class);
//        }
//
//        private void setContextActive(
//                BeanManager beanManager,
//                Class<? extends Annotation> cls) {
//            BeanManagerImpl beanManagerImpl = (BeanManagerImpl) beanManager;
////            AbstractThreadLocalMapContext context = (AbstractThreadLocalMapContext) beanManagerImpl
////                    .getContexts().get(cls).get(0);
////            context.setBeanStore(new HashMapBeanStore());
////            context.setActive(true);
//        }
//    }
}
