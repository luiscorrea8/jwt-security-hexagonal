package com.example.security.application.dto;

public class RegisterCommand {
    private final String name;
    private final String email;
    private final String password;
    private final String role;

    public RegisterCommand(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String name() { return name; }
    public String email() { return email; }
    public String password() { return password; }
    public String role() { return role; }
}
