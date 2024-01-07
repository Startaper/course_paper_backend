package com.example.course_paper_backend.enums;

import java.util.Arrays;
import java.util.List;

public enum ScheduleType {

    FULL_DAY("Полный день"),
    SHIFT("Сменный график"),
    FLEXIBLE("Гибкий график"),
    REMOTE("Удаленная работа"),
    FLY_IN_FLY_OUT("Вахтовый метод");

    private final String name;

    ScheduleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
