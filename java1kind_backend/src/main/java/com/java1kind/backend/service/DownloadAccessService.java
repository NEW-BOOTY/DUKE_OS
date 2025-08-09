/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package com.java1kind.backend.service;

import com.java1kind.backend.util.JwtTokenUtil;
import org.springframework.stereotype.Service;

@Service
public class DownloadAccessService {

    private final JwtTokenUtil jwtTokenUtil;

    public DownloadAccessService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String generateAccessToken(String sessionId) {
        return jwtTokenUtil.generateToken(sessionId);
    }

    public boolean isTokenValid(String token, String sessionId) {
        return jwtTokenUtil.validateToken(token)
                && sessionId.equals(jwtTokenUtil.extractSessionId(token));
    }

    public String getSessionIdFromToken(String token) {
        return jwtTokenUtil.extractSessionId(token);
    }
}
