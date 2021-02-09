package com.krealll.worklance.model.entity.type;

public enum OrderStatus {

    OPENED("Opened"),
    IN_PROGRESS("in progress"),
    CLOSED("Closed");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
