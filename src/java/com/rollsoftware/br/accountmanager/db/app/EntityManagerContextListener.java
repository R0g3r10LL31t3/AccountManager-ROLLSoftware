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
package com.rollsoftware.br.accountmanager.db.app;

import com.rollsoftware.br.accountmanager.properties.Resource;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
@WebListener
@ApplicationScoped
public class EntityManagerContextListener implements ServletContextListener {

    private static final String PU
            = Resource.getProperty("roll.software.br.application.database.PU");
    private static final Properties DB_PROPS = Resource.getDatabaseProperties();

    private static EntityManagerFactory EMF;
    private static final ThreadLocal<EntityManager> THREADLOCAL_EM;

    static {
        THREADLOCAL_EM = new ThreadLocal();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            if (THREADLOCAL_EM.get() != null) {
                THREADLOCAL_EM.get().close();
            }
            if (EMF != null) {
                EMF.close();
            }
        } catch (Throwable ex) {
            throw ex;
        } finally {
            THREADLOCAL_EM.remove();
        }
    }

    public static EntityManager getEntityManager() {
        if (EMF == null) {
            EMF = Persistence.createEntityManagerFactory(PU);
        }

        if (THREADLOCAL_EM.get() == null) {
            THREADLOCAL_EM.set(EMF.createEntityManager(DB_PROPS));
        }

        if (EMF == null || THREADLOCAL_EM.get() == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        return THREADLOCAL_EM.get();
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        if (EMF == null) {
            EMF = Persistence.createEntityManagerFactory(PU);
        }

        EntityManager em = EMF.createEntityManager(DB_PROPS);

        System.out.println("em.open " + em);

        return em;
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        try {
            if (em != null) {

                System.out.println("em.close " + em);
                em.close();
            }
        } catch (Throwable ex) {
            throw ex;
        }
    }
}
