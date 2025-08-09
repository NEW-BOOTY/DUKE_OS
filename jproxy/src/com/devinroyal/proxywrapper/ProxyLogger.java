/*
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */

package com.devinroyal.proxywrapper.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProxyLogger {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void log(String message) {
        System.out.println("[" + sdf.format(new Date()) + "] INFO: " + message);
    }

    public static void error(String message) {
        System.err.println("[" + sdf.format(new Date()) + "] ERROR: " + message);
    }
}
