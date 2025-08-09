/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package web;

import ai.QuantumSecureAI;
import crypto.PostQuantumCryptography;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.*;
import java.io.IOException;

public final class QAOSAIWebServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        QuantumSecureAI ai = new QuantumSecureAI(new PostQuantumCryptography());

        handler.addServletWithMapping(new ServletHolder(new HttpServlet() {
            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                String prompt = req.getParameter("prompt");

                try {
                    String encrypted = ai.respondToQuery(prompt);
                    String decrypted = ai.decryptResponse(encrypted);

                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"encrypted\":\"" + encrypted + "\",\"decrypted\":\"" + decrypted + "\"}");
                } catch (Exception e) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
        }), "/query");

        System.out.println("🌐 QAOSAI Web Server running on http://localhost:8080/query");
        server.start();
        server.join();
    }
}
