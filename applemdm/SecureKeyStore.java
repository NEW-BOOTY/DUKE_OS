/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package applemdm;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class SecureKeyStore {
    private static KeyStore keyStore;

    public static void init(String p12Path, String password) throws Exception {
        keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(p12Path)) {
            keyStore.load(fis, password.toCharArray());
        } catch (Exception e) {
            throw new Exception("Failed to load keystore: " + e.getMessage());
        }
    }

    public static SSLContext buildSSLContext() throws Exception {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new Exception("Error creating SSL context: " + e.getMessage());
        }
    }
}
