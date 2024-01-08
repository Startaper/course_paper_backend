package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.AreaType;
import com.example.course_paper_backend.enums.EducationLevel;
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
    @Column(name = "external_id", length = 1000, nullable = false)
    private String externalId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "photo_url", length = 1000)
    private String photoUrl;
    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
    private AreaEntity area;
    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false)
    private EducationLevel educationLevel;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<SiteEntity> site;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ResumeEntity> resumes;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ContactEntity> contacts;
    @OneToMany(mappedBy = "applicant", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<AreaCitiEntity> citizenship;
    @OneToMany(mappedBy = "applicant", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<AreaCitiEntity> workTickets;
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
        this.birthDate = dateFormat.parse(jsonObject.getString("birth_date"));
        this.age = jsonObject.getInt("age");
        this.area = new AreaEntity(jsonObject.getJSONObject("area"), AreaType.AREA, this);
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
//        this.workTickets = workTickets;
//        this.certificates = certificates;
    }
}
