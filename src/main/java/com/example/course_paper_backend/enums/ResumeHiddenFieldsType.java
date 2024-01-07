package com.example.course_paper_backend.enums;

public enum ResumeHiddenFieldsType {
    PHONES("Все указанные в резюме телефоны"),
    EMAIL("Электронную почту"),
    NAMES_AND_PHOTO("ФИО и фотографию"),
    OTHER_CONTACTS("Прочие контакты (Skype, ICQ, соцсети)"),
    EXPERIENCE("Все места работы");

    private final String name;

    ResumeHiddenFieldsType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
