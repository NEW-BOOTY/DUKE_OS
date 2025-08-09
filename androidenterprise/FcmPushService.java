/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package androidenterprise;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FcmPushService {

    private static final Logger logger = Logger.getLogger(FcmPushService.class.getName());

    private final String serverKey;

    public FcmPushService(String serverKey) {
        this.serverKey = serverKey;
        logger.info("[FCM] Initialized FCM Push Service.");
    }

    public void sendPushNotification(String deviceToken, String title, String body) {
        try {
            String message = buildMessage(deviceToken, title, body);
            sendPost(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[FCM] Failed to send push notification", e);
        }
    }

    private String buildMessage(String deviceToken, String title, String body) {
        return "{"
            + "\"to\":\"" + deviceToken + "\","
            + "\"notification\":{"
            + "\"title\":\"" + title + "\","
            + "\"body\":\"" + body + "\""
            + "}"
            + "}";
    }

    private void sendPost(String jsonMessage) throws Exception {
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");

        conn.setRequestProperty("Authorization", "key=" + serverKey);
        conn.setRequestProperty("Content-Type", "application/json; UTF-8");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonMessage.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            logger.info("[FCM] Push notification sent successfully.");
        } else {
            logger.warning("[FCM] Push notification failed with HTTP code: " + responseCode);
        }

        conn.disconnect();
    }
}
