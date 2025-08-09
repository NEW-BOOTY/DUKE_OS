/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package netconfig.proxy;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.*;

public final class NodePACBridge {

    private static final Logger LOGGER = Logger.getLogger(NodePACBridge.class.getName());
    private static final String LOCAL_NODE_ENDPOINT = "http://localhost:3100/resolve";

    public static String resolve(String pacUrl, String targetHost) {
        try {
            URL url = new URL(LOCAL_NODE_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            String payload = String.format("{\"pacUrl\":\"%s\", \"targetHost\":\"%s\"}", pacUrl, targetHost);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                return response.toString();
            }

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error resolving proxy via NodeJS PAC service", ex);
            return "DIRECT";
        }
    }
}

/*
 * NodePACBridge.java
 * - Sends POST request to localhost:3100/resolve
 * - Expects JSON response: e.g., "PROXY 127.0.0.1:8080" or "DIRECT"
 * - Used as a fallback or advanced method in AutoProxyConfigurator
 * - Requires pac-node-server.js to be running
 */

// Copyright © 2025 Devin B. Royal. All Rights Reserved.
