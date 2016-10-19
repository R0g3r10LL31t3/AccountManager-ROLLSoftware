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
package com.rollsoftware.br.accountmanager.db.internal.derby;

import com.rollsoftware.br.util.Utils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
public class DerbyEncrypt {

    private static void generate(Map map) {
        Map copy = new HashMap(map);
        copy.entrySet().forEach((item) -> {
            Map.Entry entry = (Map.Entry) item;
            String getKey = (String) entry.getKey();
            String getValue = (String) entry.getValue();

            if (getKey.endsWith(".encrypted")) {
                getValue = com.rollsoftware.br.util.CypherUtils.encrypt(getValue);
                map.put(getKey, getValue);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();

        java.io.InputStream inputStream = Utils.getResourceAsStream(
                "com/rollsoftware/br/accountmanager"
                + "/db/internal/derby/derby_decrypted.properties");
        properties.load(inputStream);

        generate(properties);

        String toStore = System.getProperty("user.dir") + "/src/resources/"
                + "com/rollsoftware/br/accountmanager"
                + "/db/internal/derby/derby_encrypted.properties";

        String license = "##\n"
                + "#          Copyright 2016-2026 Rogério Lecarião Leite\n"
                + "#\n"
                + "#  Licensed under the Apache License, Version 2.0 (the \"License\");\n"
                + "#  you may not use this file except in compliance with the License.\n"
                + "#  You may obtain a copy of the License at\n"
                + "#\n"
                + "#      http://www.apache.org/licenses/LICENSE-2.0\n"
                + "#\n"
                + "#  Unless required by applicable law or agreed to in writing, software\n"
                + "#  distributed under the License is distributed on an \"AS IS\" BASIS,\n"
                + "#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
                + "#  See the License for the specific language governing permissions and\n"
                + "#  limitations under the License.\n"
                + "#\n"
                + "#  CEO 2016: Rogério Lecarião Leite; ROLL Software\n"
                + "##";

        properties.store(new FileOutputStream(toStore), license);
    }
}
