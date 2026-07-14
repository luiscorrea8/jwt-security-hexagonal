package com.example.security.infrastructure.adapter.output.persistence;

import com.example.security.domain.model.Token;
import com.example.security.domain.port.output.TokenRepositoryPort;
import com.example.security.infrastructure.adapter.output.persistence.entity.TokenJpaEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class TokenRepositoryAdapter implements TokenRepositoryPort {
    private final TokenJpaRepository jpaRepository;

    public TokenRepositoryAdapter(TokenJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Token save(Token token) {
        TokenJpaEntity entity = new TokenJpaEntity();
        entity.setToken(token.getValue());
        entity.setTokenType(token.getType().name());
        entity.setTokenCategory(token.getCategory().name());
        entity.setUserId(token.getUserId());
        entity.setExpiresAt(token.getExpiresAt());
        entity.setRevoked(token.isRevoked());
        entity.setExpired(token.isExpired());
        TokenJpaEntity saved = jpaRepository.save(entity);
        return Token.builder()
            .id(saved.getId()).value(saved.getToken())
            .type(Token.TokenType.valueOf(saved.getTokenType()))
            .category(Token.TokenCategory.valueOf(saved.getTokenCategory()))
            .userId(saved.getUserId()).expiresAt(saved.getExpiresAt())
            .isRevoked(saved.isRevoked()).isExpired(saved.isExpired()).build();
    }

    @Override
    public List<Token> findAllValidTokensByUserId(Long userId) {
        return jpaRepository.findAllValidTokensByUserId(userId).stream()
            .map(e -> Token.builder().id(e.getId()).value(e.getToken())
                .type(Token.TokenType.valueOf(e.getTokenType()))
                .category(Token.TokenCategory.valueOf(e.getTokenCategory()))
                .userId(e.getUserId()).expiresAt(e.getExpiresAt())
                .isRevoked(e.isRevoked()).isExpired(e.isExpired()).build())
            .toList();
    }

    @Override
    @Transactional
    public void revokeAllUserTokens(Long userId) {
        jpaRepository.revokeAllByUserId(userId);
    }
}
