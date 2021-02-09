package com.krealll.worklance.model.entity.type;

public enum Specialization {

    IT("it"),
    ADMINISTRATION("administration"),
    FINANCES("finances"),
    ART("art"),
    MARKETING("marketing"),
    MEDICINE("medicine"),
    SCIENCE("science"),
    SECURITY("security"),
    ENGINEERING("engineering"),
    VEHICLES("vehicles"),
    OTHER("other");

    private final String name;

    Specialization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
