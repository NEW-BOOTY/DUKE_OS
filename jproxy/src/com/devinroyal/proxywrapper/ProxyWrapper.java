/*
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */

package com.devinroyal.proxywrapper;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.net.ssl.*;

public class ProxyWrapper {
    public static void main(String[] args) {
        try {
            ProxyConfig config = ProxyConfig.fromArgs(args);
            ProxyLogger.log("Starting Proxy on " + config.getListenPort() + " forwarding to " + config.getTargetHost() + ":" + config.getTargetPort());
            ProxyServer proxyServer = new ProxyServer(config);
            proxyServer.start();
        } catch (ProxyException e) {
            ProxyLogger.error("Proxy failed to start: " + e.getMessage());
        }
    }
}
