/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package applemdm;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.File;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.KeyFactory;
import java.time.Instant;
import java.util.Date;

public class ApnsTokenSigner {

    public static String generateToken(String teamId, String keyId, String pathToP8) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(pathToP8).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        PrivateKey privateKey = kf.generatePrivate(spec);

        Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam("kid", keyId)
                .setIssuer(teamId)
                .setIssuedAt(Date.from(now))
                .signWith(privateKey, SignatureAlgorithm.ES256)
                .compact();
    }
}
