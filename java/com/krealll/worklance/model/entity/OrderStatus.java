package com.krealll.worklance.model.entity;

public enum OrderStatus {

    OPENED("opened"),
    IN_PROGRESS("in_progress"),
    CLOSED("closed");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
