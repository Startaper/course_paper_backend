package com.example.course_paper_backend.enums;

public enum LevelLanguage {

    A1("Начальный", "Beginner"),
    A2("Элементарный", "Elementary"),
    B1("Cредний", "Pre-Intermediate/Intermediate"),
    B2("Cредне-продвинутый", "Upper Intermediate"),
    C1("Продвинутый", "Advanced"),
    C2("В совершенстве", "Proficiency"),
    L1("Родной", "Native");

    private final String ruName;
    private final String internationalName;

    LevelLanguage(String ruName, String internationalName) {
        this.ruName = ruName;
        this.internationalName = internationalName;
    }

    public String getRuName() {
        return ruName;
    }

    public String getInternationalName() {
        return internationalName;
    }

}
