package com.example.security.domain.model;

import java.util.List;

public class User {
    private Long id;
    private String name;
    private Email email;
    private Password password;
    private Role role;
    private List<String> scopes;

    public User() {}

    public User(Long id, String name, Email email, Password password, Role role, List<String> scopes) {
        this.id = id; this.name = name; this.email = email; this.password = password; this.role = role; this.scopes = scopes;
    }

    public static Builder builder() { return new Builder(); }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Email getEmail() { return email; }
    public Password getPassword() { return password; }
    public Role getRole() { return role; }
    public List<String> getScopes() { return scopes; }

    public static class Builder {
        private Long id; private String name; private Email email; private Password password; private Role role; private List<String> scopes;
        public Builder id(Long id){ this.id = id; return this; }
        public Builder name(String name){ this.name = name; return this; }
        public Builder email(Email email){ this.email = email; return this; }
        public Builder password(Password password){ this.password = password; return this; }
        public Builder role(Role role){ this.role = role; return this; }
        public Builder scopes(java.util.List<String> scopes){ this.scopes = scopes; return this; }
        public User build(){ return new User(id,name,email,password,role,scopes); }
    }
}
