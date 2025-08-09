/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package network;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public final class QuantumSafeNetwork {

    private static final Logger LOGGER = Logger.getLogger(QuantumSafeNetwork.class.getName());

    private final int port;
    private ServerSocket serverSocket;

    public QuantumSafeNetwork(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.info("QuantumSafeNetwork listening on port " + port);
            while (true) {
                Socket client = serverSocket.accept();
                new Thread(() -> handleClient(client)).start();
            }
        } catch (IOException e) {
            LOGGER.severe("Network error: " + e.getMessage());
            throw new Exception("QuantumSafeNetwork startup failed", e);
        }
    }

    private void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            String input = in.readLine();
            LOGGER.info("Received: " + input);
            out.write("ACK: " + input + "\n");
            out.flush();

        } catch (IOException e) {
            LOGGER.warning("Client handling failed: " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException ignored) {}
        }
    }

    public void stop() throws Exception {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
            LOGGER.info("QuantumSafeNetwork shut down");
        }
    }
}
