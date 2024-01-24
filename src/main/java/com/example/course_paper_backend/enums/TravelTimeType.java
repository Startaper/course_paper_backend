package com.example.course_paper_backend.enums;

public enum TravelTimeType {

    ANY("Любой"),
    LESS_THAN_HOUR("Меньше часа"),
    FROM_HOUR_TO_ONE_AND_HALF("От часу до полутора часов");

    private final String name;

    TravelTimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
