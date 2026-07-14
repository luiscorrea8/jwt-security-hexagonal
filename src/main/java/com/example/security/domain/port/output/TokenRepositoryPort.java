package com.example.security.domain.port.output;


import com.example.security.domain.model.Token;
import java.util.List;

public interface TokenRepositoryPort {
    Token save(Token token);
    List<Token> findAllValidTokensByUserId(Long userId);
    void revokeAllUserTokens(Long userId);
}
