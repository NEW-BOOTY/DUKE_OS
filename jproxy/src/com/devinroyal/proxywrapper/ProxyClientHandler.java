/*
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */

package com.devinroyal.proxywrapper;

import java.io.*;
import java.net.*;

public class ProxyClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ProxyConfig config;

    public ProxyClientHandler(Socket clientSocket, ProxyConfig config) {
        this.clientSocket = clientSocket;
        this.config = config;
    }

    @Override
    public void run() {
        try (
            Socket targetSocket = new Socket(config.getTargetHost(), config.getTargetPort());
            InputStream clientIn = clientSocket.getInputStream();
            OutputStream clientOut = clientSocket.getOutputStream();
            InputStream targetIn = targetSocket.getInputStream();
            OutputStream targetOut = targetSocket.getOutputStream()
        ) {
            Thread forwardClient = new Thread(() -> forward(clientIn, targetOut));
            Thread forwardTarget = new Thread(() -> forward(targetIn, clientOut));
            forwardClient.start();
            forwardTarget.start();
            forwardClient.join();
            forwardTarget.join();
        } catch (Exception e) {
            ProxyLogger.error("Proxy client handler error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }

    private void forward(InputStream in, OutputStream out) {
        byte[] buffer = new byte[8192];
        int bytesRead;
        try {
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();
            }
        } catch (IOException e) {
            // Connection closed
        }
    }
}
