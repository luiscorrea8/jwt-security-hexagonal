package com.example.security.application.dto;

public class TokenResult {
    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;
    private final long expiresInSeconds;

    public TokenResult(String accessToken, String refreshToken, String tokenType, long expiresInSeconds) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String accessToken() { return accessToken; }
    public String refreshToken() { return refreshToken; }
    public String tokenType() { return tokenType; }
    public long expiresInSeconds() { return expiresInSeconds; }
}
