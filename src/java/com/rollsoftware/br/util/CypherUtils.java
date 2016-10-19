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
package com.rollsoftware.br.util;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
public final class CypherUtils {

    private CypherUtils() {
    }

    private static String encrypt0(String decrypted) {
        Object instance;
        Class clazz;

        try {
            clazz = Class.forName("com.rollsoftware.br.cipher.CypherUtils");
            java.lang.reflect.Constructor constructor
                    = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
            java.lang.reflect.Method encrypt
                    = clazz.getMethod("encrypt", new Class[]{String.class});
            return (String) encrypt.invoke(instance, decrypted);
        } catch (Throwable ex) {
            return decrypted;
        }
    }

    private static String encrypt0(
            String key, String initVector, String decrypted) {
        Object instance;
        Class clazz;

        try {
            clazz = Class.forName("com.rollsoftware.br.cipher.CypherUtils");
            java.lang.reflect.Constructor constructor
                    = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
            java.lang.reflect.Method encrypt
                    = clazz.getMethod("encrypt", new Class[]{
                String.class, String.class, String.class
            });
            return (String) encrypt.invoke(instance, key, initVector, decrypted);
        } catch (Throwable ex) {
            return decrypted;
        }
    }

    private static String decrypt0(String encrypted) {
        Object instance;
        Class clazz;

        try {
            clazz = Class.forName("com.rollsoftware.br.cipher.CypherUtils");
            java.lang.reflect.Constructor constructor
                    = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
            java.lang.reflect.Method decrypt
                    = clazz.getMethod("decrypt", new Class[]{String.class});
            return (String) decrypt.invoke(instance, encrypted);
        } catch (Throwable ex) {
            return encrypted;
        }
    }

    private static String decrypt0(
            String key, String initVector, String encrypted) {
        Object instance;
        Class clazz;

        try {
            clazz = Class.forName("com.rollsoftware.br.cipher.CypherUtils");
            java.lang.reflect.Constructor constructor
                    = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
            java.lang.reflect.Method decrypt
                    = clazz.getMethod("decrypt", new Class[]{
                String.class, String.class, String.class
            });
            return (String) decrypt.invoke(instance, key, initVector, encrypted);
        } catch (Throwable ex) {
            return encrypted;
        }
    }

    public static String encrypt(String value) {
        return encrypt0(value);
    }

    public static String encrypt(
            String key, String initVector, String decrypted) {
        return encrypt0(key, initVector, decrypted);
    }

    public static String decrypt(String encrypted) {
        return decrypt0(encrypted);
    }

    public static String decrypt(
            String key, String initVector, String encrypted) {
        return decrypt0(key, initVector, encrypted);
    }
}