package com.krealll.worklance.model.entity.type;

public enum UserRole {

    GUEST("guest"),
    USER("user"),
    MANAGER("manager");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
