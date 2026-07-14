package com.example.security.domain.model;

public enum Role {
    USER, ADMIN;

    public static Role fromString(String v){
        if (v == null) return USER;
        try { return Role.valueOf(v); } catch(Exception e){
            switch(v.toLowerCase()){
                case "admin": return ADMIN;
                default: return USER;
            }
        }
    }
}
