package com.example.course_paper_backend.enums;

public enum ContactType {

    HOME("Домашний телефон"),
    WORK("Рабочий телефон"),
    EMAIL("Эл. почта"),
    CELL("Мобильный телефон");

    private final String name;

    ContactType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
