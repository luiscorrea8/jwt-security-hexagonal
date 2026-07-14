package com.example.security.domain.port.output;

import com.example.security.domain.model.User;

public interface TokenGeneratorPort {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    long getAccessTokenExpirationSeconds();
    long getRefreshTokenExpirationSeconds();
}
