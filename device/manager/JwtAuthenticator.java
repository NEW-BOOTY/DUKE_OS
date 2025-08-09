/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package device.manager;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class JwtAuthenticator {
    private static final SecretKey key;

    static {
        try {
            String secret = new String(Files.readAllBytes(Paths.get("resources/auth.secret"))).trim();
            key = Keys.hmacShaKeyFor(secret.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JWT secret: " + e.getMessage());
        }
    }

    public static boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1hr
                .signWith(key)
                .compact();
    }
}
