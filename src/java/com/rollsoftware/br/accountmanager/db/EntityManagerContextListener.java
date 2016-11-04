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
package com.rollsoftware.br.accountmanager.db;

import com.rollsoftware.br.accountmanager.properties.Resource;
import java.util.Properties;
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
public class EntityManagerContextListener implements ServletContextListener {

    private static final String PU
            = Resource.getProperty("roll.software.br.application.database.PU");
    private static final Properties DB_PROPS = Resource.getDatabaseProperties();

    private static EntityManagerFactory EMF;
    private static EntityManager EM;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            EMF = Persistence.createEntityManagerFactory(PU);
            EM = EMF.createEntityManager(DB_PROPS);
        } catch (Throwable ex) {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            EM.close();
            EMF.close();
        } catch (Throwable ex) {
            ex.printStackTrace(System.err);
        }
    }

    //@Produces
    //@RequestScoped
    public static EntityManager getEntityManager() {
        if (EMF == null || EM == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        return EM;
    }

}
