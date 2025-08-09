/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package applemdm;

import com.eatthepath.pushy.apns.*;
import com.eatthepath.pushy.apns.auth.ApnsSigningKey;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import io.netty.util.concurrent.Future;
import javax.net.ssl.SSLContext;
import java.io.File;

public class ApnsPushService {
    private final ApnsClient apnsClient;
    private final String apnsTopic;

    public ApnsPushService(MdmConfig config) throws Exception {
        try {
            ApnsSigningKey signingKey = ApnsSigningKey.loadFromPkcs8File(
                new File(config.getApnsKeyPath()), config.getTeamId(), config.getKeyId()
            );

            this.apnsClient = new ApnsClientBuilder()
                    .setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
                    .setSigningKey(signingKey)
                    .build();

            this.apnsTopic = config.getApnsTopic();
        } catch (Exception e) {
            throw new Exception("Failed to initialize APNs client: " + e.getMessage());
        }
    }

    public void connect() throws Exception {
        Future<Void> connectFuture = apnsClient.connect(ApnsClientBuilder.DEVELOPMENT_APNS_HOST);
        connectFuture.await();
        if (!connectFuture.isSuccess()) {
            throw new Exception("Failed to connect to APNs: " + connectFuture.cause());
        }
    }

    public void sendTestNotification(String deviceToken) throws Exception {
        String payload = "{\"aps\":{\"alert\":\"MDM Test Push\",\"sound\":\"default\"}}";

        SimpleApnsPushNotification notification = new SimpleApnsPushNotification(
                deviceToken, apnsTopic, payload
        );

        apnsClient.sendNotification(notification).addListener(response -> {
            if (response.isSuccess()) {
                System.out.println("[APNs] Push notification accepted.");
            } else {
                System.err.println("[APNs] Rejected: " + response.getRejectionReason());
            }
        });
    }

    public void shutdown() {
        if (apnsClient != null) {
            apnsClient.close();
        }
    }
}
