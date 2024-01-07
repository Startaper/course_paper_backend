package com.example.course_paper_backend.enums;

public enum TravelTimeType {

    ANY(""),
    LESS_THAN_HOUR(""),
    FROM_HOUR_TO_ONE_AND_HALF("");

    private final String name;

    TravelTimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
