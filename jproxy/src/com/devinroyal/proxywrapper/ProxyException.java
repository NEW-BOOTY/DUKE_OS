/*
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */

package com.devinroyal.proxywrapper;

public class ProxyException extends Exception {
    public ProxyException(String message) {
        super(message);
    }

    public ProxyException(String message, Throwable cause) {
        super(message, cause);
    }
}
