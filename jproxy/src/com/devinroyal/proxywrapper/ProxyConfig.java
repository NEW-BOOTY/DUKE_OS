/*
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */

package com.devinroyal.proxywrapper;

public class ProxyConfig {
    private final String targetHost;
    private final int targetPort;
    private final int listenPort;

    public ProxyConfig(String targetHost, int targetPort, int listenPort) {
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.listenPort = listenPort;
    }

    public static ProxyConfig fromArgs(String[] args) throws ProxyException {
        if (args.length != 3) {
            throw new ProxyException("Usage: java ProxyWrapper <targetHost> <targetPort> <listenPort>");
        }
        try {
            return new ProxyConfig(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } catch (NumberFormatException e) {
            throw new ProxyException("Invalid port number", e);
        }
    }

    public String getTargetHost() {
        return targetHost;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public int getListenPort() {
        return listenPort;
    }
}
