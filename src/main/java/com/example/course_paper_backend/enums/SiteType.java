package com.example.course_paper_backend.enums;

public enum SiteType {

    PERSONAL("Другой сайт"),
    MOI_KRUG("Мой круг"),
    LIVE_JOURNAL("LiveJournal"),
    LINKEDIN("LinkedIn"),
    FREELANCE("Free-lance"),
    SKYPE("Skype"),
    ISQ("ISQ");


    private final String name;

    SiteType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
