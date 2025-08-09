/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package device.manager;

import applemdm.ApnsPushService;
import androidenterprise.FcmPushService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HttpServerRouter {

    private final ApnsPushService apnsPush;
    private final FcmPushService fcmPush;

    public HttpServerRouter(ApnsPushService apnsPush, FcmPushService fcmPush) {
        this.apnsPush = apnsPush;
        this.fcmPush = fcmPush;
    }

    public void start(int port) throws Exception {
        Server server = new Server(port);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                baseRequest.setHandled(true);

                switch (target) {
                    case "/mdm/push":
                        apnsPush.sendPush("{\"mdm\":\"DeviceWipe\"}");
                        response.getWriter().println("{\"status\":\"MDM push sent\"}");
                        break;
                    case "/fcm/test":
                        fcmPush.sendPushNotification("fcm-token-placeholder", "Server Notice", "Test Message");
                        response.getWriter().println("{\"status\":\"FCM sent\"}");
                        break;
                    default:
                        response.getWriter().println("{\"status\":\"Unknown endpoint\"}");
                }
            }
        });

        server.start();
        System.out.println("🌐 Embedded HTTP server started on http://localhost:" + port);
    }
}
