package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.*;
import com.example.course_paper_backend.model.Resume;
import jakarta.persistence.*;
import lombok.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "resumes")
public class ResumeEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "external_id", length = 1000, nullable = false)
    private String externalId;
    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false)
    private ApplicantEntity applicant;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ResumeStatus status;
    @Column(name = "title", length = 1000, nullable = false)
    private String title;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    @Column(name = "skills", length = 1000, nullable = false)
    private String skills;
    @Column(name = "skill_set", length = 1000, nullable = false)
    // В БД хранится как строка с разделителем ','
    private String skillSet;
    @Column(name = "alternate_url", length = 1000, nullable = false)
    private String alternateUrl;
    @Column(name = "salary")
    private int salary;
    @Column(name = "can_view_full_info", nullable = false)
    private boolean canViewFullInfo;
    @Column(name = "favorited", nullable = false)
    private boolean favorited;
    @Column(name = "viewed", nullable = false)
    private boolean viewed;
    @Column(name = "metro", length = 1000)
    private String metro;
    @Column(name = "url_download_pdf", length = 1000, nullable = false)
    private String urlDownloadPdf;
    @Column(name = "url_download_rtf", length = 1000, nullable = false)
    private String urlDownloadRtf;
    @Enumerated(EnumType.STRING)
    @Column(name = "travel_time", nullable = false)
    private TravelTimeType travelTime;
    // В БД хранится как строка с разделителем ','
    @Column(name = "schedules", length = 1000, nullable = false)
    private String schedules;
    // В БД хранится как строка с разделителем ','
    @Column(name = "employments", length = 1000, nullable = false)
    private String employments;
    // В БД хранится как строка с разделителем ','
    @Column(name = "hidden_fields", length = 1000)
    private String hiddenFields;
    // В БД хранится как строка с разделителем ','
    @Column(name = "driver_license_types", length = 1000)
    private String driverLicenseTypes;
    @Column(name = "total_experience_in_month")
    private int totalExperienceInMonth;
    @Column(name = "negotiations_history_url", length = 1000, nullable = false)
    private String negotiationsHistoryUrl;
    @Column(name = "resume_locale", length = 1000, nullable = false)
    private String resumeLocale;
    @Enumerated(EnumType.STRING)
    @Column(name = "business_trip_readiness", nullable = false)
    private BusinessTripReadinessType businessTripReadiness;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<PaidServicesEntity> paidServices;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<RecommendationEntity> recommendations;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ExperienceEntity> experience;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<SpecializationEntity> specializations;

    public Resume toModel() {
        return new Resume().toBuilder()
                .id(this.getId())
                .title(this.getTitle())
                .skills(this.getSkills())
                .lastName(this.getApplicant().getLastName())
                .firstName(this.getApplicant().getFirstName())
                .middleName(this.getApplicant().getMiddleName())
                .resumeLocale(this.getResumeLocale())
                .alternateUrl(this.getAlternateUrl())
                .urlDownloadPdf(this.getUrlDownloadPdf())
                .urlDownloadRtf(this.getUrlDownloadRtf())
                .photo(this.getApplicant().getPhotoUrl())
                .negotiationsHistoryUrl(this.getNegotiationsHistoryUrl())
                .skillSet(this.getSkillSet().split(","))
                .driverLicenseTypes(this.getDriverLicenseTypes() == null ? new String[]{} : this.getDriverLicenseTypes().split(","))
                .schedules(this.getSchedules().split(","))
                .hiddenFields(this.getHiddenFields() == null ? new String[]{} : this.getHiddenFields().split(","))
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .birthDate(this.getApplicant().getBirthDate())
                .age(this.getApplicant().getAge())
                .salary(this.getSalary())
                .totalExperienceInMonth(this.getTotalExperienceInMonth())
                .viewed(this.isViewed())
                .favorited(this.isFavorited())
                .canViewFullInfo(this.isCanViewFullInfo())
                .status(this.getStatus())
                .travelTime(this.getTravelTime())
                .educationLevel(this.getApplicant().getEducationLevel())
                .businessTripReadiness(this.getBusinessTripReadiness())
                .employments(this.getEmployments().split(","))
                .area(this.getApplicant().getArea().toModel())
                .site(this.getApplicant().getSite().stream()
                        .map(SiteEntity::toModel)
                        .toList())
                .certificates(this.getApplicant().getCertificates().stream()
                        .map(CertificateEntity::toModel)
                        .toList())
                .educations(this.getApplicant().getEducations().stream()
                        .map(EducationEntity::toModel)
                        .toList())
                .contacts(this.getApplicant().getContacts().stream()
                        .map(ContactEntity::toModel)
                        .toList())
                .citizenship(this.getApplicant().getCitizenship().stream()
                        .map(AreaCitiEntity::toModel)
                        .toList())
                .workTickets(this.getApplicant().getWorkTickets().stream()
                        .map(AreaCitiEntity::toModel)
                        .toList())
                .languages(this.getApplicant().getLanguages().stream()
                        .map(LanguageEntity::toModel)
                        .toList())
                .specializations(this.getSpecializations().stream()
                        .map(SpecializationEntity::toModel)
                        .toList())
                .recommendations(this.getRecommendations().stream()
                        .map(RecommendationEntity::toModel)
                        .toList())
                .paidServices(this.getPaidServices().stream()
                        .map(PaidServicesEntity::toModel)
                        .toList())
                .experience(this.getExperience().stream()
                        .map(ExperienceEntity::toModel)
                        .toList())
                .build();
    }

    public ResumeEntity(JSONObject jsonObject, SimpleDateFormat dateFormat) throws JSONException, ParseException {
//        this.id = UUID.fromString(jsonObject.getString("id"));
//        Заполнение этого поля реализован в AdminService -> getApplicantFromJSONObject()
//        this.applicant = applicant;
        this.title = jsonObject.getString("title");
        this.createdAt = dateFormat.parse(jsonObject.getString("created_at"));
        this.updatedAt = dateFormat.parse(jsonObject.getString("updated_at"));
        this.skills = jsonObject.getString("skills");
        this.skillSet = convertSkillSetToString(jsonObject.getJSONArray("skill_set"));
        this.alternateUrl = jsonObject.getString("alternate_url");
        this.salary = jsonObject.optInt("salary");
        this.canViewFullInfo = jsonObject.getBoolean("can_view_full_info");
        this.favorited = jsonObject.getBoolean("favorited");
        this.viewed = jsonObject.optBoolean("viewed");
        this.metro = jsonObject.optJSONObject("metro") != null ? jsonObject.getJSONObject("metro").optString("name") : null;
        this.urlDownloadPdf = jsonObject.getJSONObject("download").getJSONObject("pdf").getString("url");
        this.urlDownloadRtf = jsonObject.getJSONObject("download").getJSONObject("rtf").getString("url");
        this.travelTime = TravelTimeType.valueOf(jsonObject.getJSONObject("travel_time").getString("id").toUpperCase());
//        Заполнение этих полей реализовано в AdminService -> add()
//        this.schedules = schedules;
//        this.employments = employments;
//        this.hiddenFields = hiddenFields;
//        this.driverLicenseTypes = driverLicenseTypes;
        this.totalExperienceInMonth = jsonObject.optInt("total_experience_in_month");
        this.negotiationsHistoryUrl = jsonObject.getJSONObject("negotiations_history").getString("url");
        this.resumeLocale = jsonObject.getJSONObject("resume_locale").getString("id");
        this.businessTripReadiness = BusinessTripReadinessType.valueOf(jsonObject.getJSONObject("business_trip_readiness")
                .getString("id").toUpperCase());
//        Заполнение этих объектов реализован в AdminService -> convertFromJSONObjectTo{Entity}List()
//        this.paidServices = paidServices;
//        this.recommendations = recommendations;
//        this.experience = experience;
//        this.specializations = specializations;
    }

    private String convertSkillSetToString(JSONArray jsonArray) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        if (jsonArray == null) return "";
        for (int i = 0; i < jsonArray.length(); i++) {
            stringBuilder.append(jsonArray.getString(i));
            if (i != jsonArray.length() - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

}
