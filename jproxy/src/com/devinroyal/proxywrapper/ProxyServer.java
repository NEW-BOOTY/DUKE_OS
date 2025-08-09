/*
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */

package com.devinroyal.proxywrapper;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class ProxyServer {
    private final ProxyConfig config;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    public ProxyServer(ProxyConfig config) {
        this.config = config;
    }

    public void start() throws ProxyException {
        try (ServerSocket serverSocket = new ServerSocket(config.getListenPort())) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.submit(new ProxyClientHandler(clientSocket, config));
            }
        } catch (IOException e) {
            throw new ProxyException("Error starting proxy server", e);
        }
    }
}
