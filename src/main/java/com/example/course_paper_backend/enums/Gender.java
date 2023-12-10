package com.example.course_paper_backend.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String genderStr;

    Gender(String gender) {
        this.genderStr = gender;
    }

}
