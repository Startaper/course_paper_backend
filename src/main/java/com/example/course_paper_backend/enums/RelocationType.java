package com.example.course_paper_backend.enums;

public enum RelocationType {

    NO_RELOCATION("Не готов к переезду"),
    RELOCATION_POSSIBLE("Готов к переезду"),
    RELOCATION_DESIRABLE("Хочу переехать");

    private final String name;

    RelocationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
