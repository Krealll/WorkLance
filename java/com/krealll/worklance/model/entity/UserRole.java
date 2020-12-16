package com.krealll.worklance.model.entity;

public enum UserRole {

    GUEST("guest"),
    USER("user"),
    ADMIN("admin");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
