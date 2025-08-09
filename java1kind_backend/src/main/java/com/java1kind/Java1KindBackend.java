/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */
package com.java1kind.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Java1KindBackend {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Java1KindBackend.class, args);
            System.out.println("Java 1 KIND Backend Server started successfully.");
        } catch (Exception e) {
            System.err.println("Fatal error occurred during backend startup: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
