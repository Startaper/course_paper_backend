package com.example.course_paper_backend.model;

import com.example.course_paper_backend.enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Resume {

    private UUID id;
    private String area;
    private String title;
    private String metro;
    private String photo;
    private String skills;
    private String skillSet;
    private String lastName;
    private String firstName;
    private String schedules;
    private String middleName;
    private String employments;
    private String workTickets;
    private String citizenship;
    private String urlDownloadPdf;
    private String urlDownloadRtf;
    private String specializations;
    private String driverLicenseTypes;
    private Date createdAt;
    private Date updatedAt;
    private Date birthDate;
    private int age;
    private int salary;
    private float rating;
    private int totalExperienceInMonth;
    private boolean viewed;
    private boolean favorited;
    private boolean canViewFullInfo;
    private Gender gender;
    private ResumeStatus status;
    private TravelTimeType travelTime;
    private EducationLevel educationLevel;
    private BusinessTripReadinessType businessTripReadiness;
    private List<Site> site;
    private List<Contact> contacts;
    private List<Language> languages;
    private List<Education> educations;
    private List<Experience> experience;
    private List<Recommendation> recommendations;

}
