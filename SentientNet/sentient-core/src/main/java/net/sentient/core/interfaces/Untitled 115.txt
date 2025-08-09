/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */
package net.sentient.core.interfaces;

/**
 * Defines the interface for launching a REST-based interface for SentientNet.
 */
public interface RestInterface {

    /**
     * Starts the REST interface server.
     *
     * @throws Exception if the REST interface fails to start.
     */
    void start() throws Exception;
}
