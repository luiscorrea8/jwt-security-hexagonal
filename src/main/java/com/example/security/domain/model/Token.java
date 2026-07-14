package com.example.security.domain.model;

import java.time.Instant;

public class Token {
    private Long id; private String value; private TokenType type; private TokenCategory category; private Long userId; private Instant expiresAt; private boolean isRevoked; private boolean isExpired;

    public enum TokenType{ ACCESS, REFRESH }
    public enum TokenCategory{ ACCESS, REFRESH }

    public static Builder builder(){ return new Builder(); }

    public static class Builder{
        private Long id; private String value; private TokenType type; private TokenCategory category; private Long userId; private Instant expiresAt; private boolean isRevoked; private boolean isExpired;
        public Builder id(Long id){ this.id = id; return this; }
        public Builder value(String value){ this.value = value; return this; }
        public Builder type(TokenType t){ this.type = t; return this; }
        public Builder category(TokenCategory c){ this.category = c; return this; }
        public Builder userId(Long u){ this.userId = u; return this; }
        public Builder expiresAt(Instant e){ this.expiresAt = e; return this; }
        public Builder isRevoked(boolean r){ this.isRevoked = r; return this; }
        public Builder isExpired(boolean e){ this.isExpired = e; return this; }
        public Token build(){ Token t = new Token(); t.id = id; t.value = value; t.type = type; t.category = category; t.userId = userId; t.expiresAt = expiresAt; t.isRevoked = isRevoked; t.isExpired = isExpired; return t; }
    }

    public Long getId(){ return id; }
    public String getValue(){ return value; }
    public TokenType getType(){ return type; }
    public TokenCategory getCategory(){ return category; }
    public Long getUserId(){ return userId; }
    public Instant getExpiresAt(){ return expiresAt; }
    public boolean isRevoked(){ return isRevoked; }
    public boolean isExpired(){ return isExpired; }
}
