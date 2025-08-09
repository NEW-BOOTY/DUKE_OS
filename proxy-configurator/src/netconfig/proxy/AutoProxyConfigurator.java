/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package netconfig.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public final class AutoProxyConfigurator {

    private static final Logger LOGGER = Logger.getLogger(AutoProxyConfigurator.class.getName());

    private static final String PAC_SCRIPT_URL = System.getenv("JAVA_PAC_URL");
    private static final String STATIC_PROXY = System.getenv("JAVA_PROXY"); // e.g. "http://127.0.0.1:8888"

    private static Proxy globalProxy = Proxy.NO_PROXY;

    static {
        try {
            configure();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Proxy configuration failed", e);
        }
    }

    public static void configure() throws Exception {
        if (PAC_SCRIPT_URL != null && !PAC_SCRIPT_URL.isEmpty()) {
            configureWithPAC(PAC_SCRIPT_URL);
        } else if (STATIC_PROXY != null && !STATIC_PROXY.isEmpty()) {
            configureStaticProxy(STATIC_PROXY);
        } else {
            LOGGER.warning("No proxy configuration provided. Using direct connection.");
        }
    }

    private static void configureStaticProxy(String proxyUrl) throws URISyntaxException {
        URI uri = new URI(proxyUrl);
        String host = uri.getHost();
        int port = uri.getPort();

        if (host == null || port == -1) {
            throw new IllegalArgumentException("Invalid proxy URI: " + proxyUrl);
        }

        SocketAddress address = new InetSocketAddress(host, port);
        globalProxy = new Proxy(Proxy.Type.HTTP, address);

        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                return Collections.singletonList(globalProxy);
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                LOGGER.log(Level.WARNING, "Proxy connection failed: " + uri, ioe);
            }
        });

        LOGGER.info("Static proxy configured: " + proxyUrl);
    }

    private static void configureWithPAC(String pacUrl) throws IOException {
        // Simplified PAC support via external script parser
        ProcessBuilder pb = new ProcessBuilder("node", "pac-resolver.js", pacUrl);
        pb.redirectErrorStream(true);
        Process proc = pb.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String proxyInstruction = reader.readLine(); // e.g. "PROXY 192.168.1.1:8080"
            if (proxyInstruction != null && proxyInstruction.startsWith("PROXY")) {
                String[] parts = proxyInstruction.split(" ");
                String[] hostPort = parts[1].split(":");
                configureStaticProxy("http://" + hostPort[0] + ":" + hostPort[1]);
            } else {
                LOGGER.warning("PAC script did not return a usable proxy. Falling back to direct.");
            }
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed reading PAC proxy configuration.", ex);
        }
    }

    public static Proxy getGlobalProxy() {
        return globalProxy;
    }

    public static boolean isProxyConfigured() {
        return globalProxy != Proxy.NO_PROXY;
    }
}

/*
 * Features:
 * - Supports static proxy via JAVA_PROXY (e.g., http://127.0.0.1:8888)
 * - Supports PAC script parsing via external Node.js helper (optional, not embedded here)
 * - Sets system-wide ProxySelector override
 * - Logs every proxy operation failure or success
 * - Exposes `getGlobalProxy()` for internal application logic to use explicitly if needed
 * 
 * Usage:
 * - Define JAVA_PROXY or JAVA_PAC_URL in your environment or start script
 * - Import and call AutoProxyConfigurator.configure() if you want manual invocation
 */

// Copyright © 2025 Devin B. Royal. All Rights Reserved.
