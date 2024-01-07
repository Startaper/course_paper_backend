package com.example.course_paper_backend.enums;

public enum BusinessTripReadinessType {
    READY("Готов к командировкам"),
    SOMETIMES("Готов к редким командировкам"),
    NEVER("Не готов к командировкам");

    private final String name;

    BusinessTripReadinessType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
