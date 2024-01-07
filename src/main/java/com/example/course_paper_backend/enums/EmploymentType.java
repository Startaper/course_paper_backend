package com.example.course_paper_backend.enums;

import com.example.course_paper_backend.model.Employment;

import java.util.Arrays;
import java.util.List;

public enum EmploymentType {

    FULL("Полная занятость"),
    PART("Частичная занятость"),
    PROJECT("Проектная работа"),
    VOLUNTEER("Волонтерство"),
    PROBATION("Стажировка");

    private final String name;

    EmploymentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public static EmploymentType getByName(String name) {
        return EmploymentType.valueOf(name);
    }

    public static List<Employment> convertFromArrayToList(String[] arr) {
        List<EmploymentType> types = Arrays.stream(arr).map(EmploymentType::getByName).toList();
        return types.stream()
                .map(e -> new Employment().toBuilder()
                        .id(e.toString())
                        .name(e.getName())
                        .build())
                .toList();
    }
}
