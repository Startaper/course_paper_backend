package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.EducationLevel;
import com.example.course_paper_backend.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "applicants")
public class ApplicantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "area", length = 500)
    private String area;
    @Column(name = "photo_url", length = 1000)
    private String photoUrl;
    @Column(name = "external_id", length = 1000, nullable = false)
    private String externalId;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "citizenship", length = 1000)
    private String citizenship;
    @Column(name = "workTickets", length = 1000)
    private String workTickets;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false)
    private EducationLevel educationLevel;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<SiteEntity> site;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ResumeEntity> resumes;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ContactEntity> contacts;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<LanguageEntity> languages;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<EducationEntity> educations;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<CertificateEntity> certificates;

    public ApplicantEntity(JSONObject jsonObject, SimpleDateFormat dateFormat) throws JSONException, ParseException {
//        this.id = UUID.fromString(jsonObject.getString("id"));
        this.lastName = jsonObject.getString("last_name");
        this.firstName = jsonObject.getString("first_name");
        this.middleName = jsonObject.optString("middle_name");
        this.gender = Gender.valueOf(jsonObject.getJSONObject("gender").getString("id").toUpperCase());
        this.birthDate = dateFormat.parse(jsonObject.getString("birth_date"));
        this.age = jsonObject.getInt("age");
        this.area = jsonObject.getJSONObject("area").getString("name");
        this.educationLevel = EducationLevel.valueOf(jsonObject.getJSONObject("education").getJSONObject("level")
                .getString("id").toUpperCase());
        this.photoUrl = jsonObject.optJSONObject("photo") != null ? jsonObject.getJSONObject("photo").optString("medium") : null;
//        Заполнение этих объектов реализован в AdminService -> convertFromJSONObjectTo{Entity}List()
//        this.site = site;
//        this.resumes = resumes;
//        this.contacts = contacts;
//        this.languages = languages;
//        this.educations = educations;
//        this.citizenship = citizenship;
//        Заполнение этих полей реализовано в AdminService -> add()
//        this.workTickets = workTickets;
//        this.certificates = certificates;
    }
}
