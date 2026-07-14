package com.example.security.infrastructure.adapter.output.security;

import com.example.security.domain.model.User;
import com.example.security.domain.port.output.TokenGeneratorPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenAdapter implements TokenGeneratorPort {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long accessTokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Override
    public String generateAccessToken(User user) { return buildToken(user, accessTokenExpiration); }

    @Override
    public String generateRefreshToken(User user) { return buildToken(user, refreshTokenExpiration); }

    @Override
    public long getAccessTokenExpirationSeconds() { return accessTokenExpiration / 1000; }

    @Override
    public long getRefreshTokenExpirationSeconds() { return refreshTokenExpiration / 1000; }

    private String buildToken(User user, long expiration) {
        return Jwts.builder()
            .setSubject("user-" + user.getId())
            .claim("roles", List.of(user.getRole().name()))
            .claim("scopes", user.getScopes())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey())
            .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
