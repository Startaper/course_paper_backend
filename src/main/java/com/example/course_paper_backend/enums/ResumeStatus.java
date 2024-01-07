package com.example.course_paper_backend.enums;

public enum ResumeStatus {

    NEW("Новый"),
    DOES_NOT_MEET_THE_REQUIREMENTS("Не соответствует требованиям"),
    AT_WORK("В работе"),
    OFFERED_A_JOB("Предложена вакансия"),
    REFUSED("Отказался");

    private final String name;

    ResumeStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
