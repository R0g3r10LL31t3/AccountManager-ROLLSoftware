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
package com.rollsoftware.br.accountmanager;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
@ApplicationPath("/rest/*")

public class AccountManagerJAXRS extends ResourceConfig {

    public AccountManagerJAXRS() {
        packages("com.rollsoftware.br.accountmanager;"
                + "com.rollsoftware.br.accountmanager.filter;"
                + "com.rollsoftware.br.accountmanager.service");
    }
}
