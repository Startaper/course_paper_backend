package com.example.course_paper_backend.enums;

public enum EducationLevel {

    SECONDARY("Среднее"),
    SPECIAL_SECONDARY("Среднее специальное"),
    UNFINISHED_HIGHER("Неоконченное высшее"),
    HIGHER("Высшее"),
    BACHELOR("Бакалавр"),
    MASTER("Магистр"),
    CANDIDATE("Кандидат наук"),
    DOCTOR("Доктор наук");

    private final String name;

    EducationLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
