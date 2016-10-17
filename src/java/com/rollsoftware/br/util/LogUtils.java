/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rollsoftware.br.util;

/**
 *
 * @author Rogério
 */
public class LogUtils {

    static {
        java.net.URL url = Utils.getResource("resources/log/log4j2.xml");
        System.setProperty("log4j.configurationFile", url.toExternalForm());
    }
}
