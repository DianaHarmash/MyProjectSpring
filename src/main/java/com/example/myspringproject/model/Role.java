package com.example.myspringproject.model;

public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String name;
    Role(String name){ this.name = name;}
    public String getName() { return this.name; }
}
