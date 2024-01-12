package com.example.course_paper_backend.model;

import com.example.course_paper_backend.enums.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Resume {

    private UUID id;
    private String title;
    private String metro;
    private String skills;
    private String lastName;
    private String firstName;
    private String middleName;
    private String resumeLocale;
    private String alternateUrl;
    private String urlDownloadPdf;
    private String urlDownloadRtf;
    private String photo;
    private String negotiationsHistoryUrl;
    // В БД хранится как строка с разделителем ','
    private String[] skillSet;
    // В БД хранится как строка с разделителем ','
    private String[] driverLicenseTypes;
    // В БД хранится как строка с разделителем ','
    private String[] schedules;
    // В БД хранится как строка с разделителем ','
    private String[] hiddenFields;
    private Date createdAt;
    private Date updatedAt;
    private Date birthDate;
    private int age;
    private int salary;
    private int totalExperienceInMonth;
    private boolean viewed;
    private boolean favorited;
    private boolean canViewFullInfo;
    private String area;
    private Gender gender;
    private ResumeStatus status;
    private TravelTimeType travelTime;
    // В БД хранится как строка с разделителем ','
    private String[] employments;
    private EducationLevel educationLevel;
    private BusinessTripReadinessType businessTripReadiness;
    private List<Site> site;
    private List<Area> workTickets;
    private List<Area> citizenship;
    private List<Contact> contacts;
    private List<Language> languages;
    private List<Education> educations;
    private List<Experience> experience;
    private List<Certificate> certificates;
    private List<PaidServices> paidServices;
    private List<Recommendation> recommendations;
    private List<Specialization> specializations;

}
