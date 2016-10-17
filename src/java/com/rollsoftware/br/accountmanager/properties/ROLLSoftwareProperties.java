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
package com.rollsoftware.br.accountmanager.properties;

import com.rollsoftware.br.util.Utils;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
public class ROLLSoftwareProperties {

    private ROLLSoftwareProperties() {
    }

    public static Properties getProperties() {
        if (Singleton.INSTANCE.isEmpty()) {
            try {
                java.io.InputStream inputStream = Utils.getResourceAsStream(
                        "com/rollsoftware/br/accountmanager"
                        + "/properties/application.properties");

                Singleton.INSTANCE.load(inputStream);

                Singleton.INSTANCE.entrySet().forEach((entry) -> {
                    String getKey = (String) entry.getKey();
                    String getValue = (String) entry.getValue();
                    while (getValue.contains("${")) {
                        int indexOf1 = getValue.lastIndexOf("${");
                        int indexOf2 = getValue.indexOf("}", indexOf1);
                        String replaceKey = getValue.substring(
                                indexOf1, indexOf2 + 1);
                        String key = replaceKey.substring(
                                2, replaceKey.length() - 1);
                        String value = Singleton.INSTANCE.getProperty(key);
                        getValue = getValue.replace(replaceKey, value);
                        Singleton.INSTANCE.setProperty(getKey, getValue);
                    }
                });

            } catch (IOException ex) {
                throw new Error(ex);
            }
        }
        return Singleton.INSTANCE;
    }

    private static interface Singleton {

        public static Properties INSTANCE = new Properties();
    }

}
