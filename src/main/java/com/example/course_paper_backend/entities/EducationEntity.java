package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.EducationType;
import com.example.course_paper_backend.model.Education;
import jakarta.persistence.*;
import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "educations")
public class EducationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EducationType type;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "name_id")
    private String nameId;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "result")
    private String result;
    @Column(name = "result_id")
    private String resultId;
    @Column(name = "organization")
    private String organization;
    @Column(name = "organization_id")
    private String organizationId;
    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false)
    private ApplicantEntity applicant;

    public Education toModel() {
        return new Education().toBuilder()
                .id(this.getId())
                .type(this.getType())
                .name(this.getName())
                .nameId(this.getNameId())
                .organization(this.getOrganization())
                .organizationId(this.getOrganizationId())
                .result(this.getResult())
                .resultId(this.getResultId())
                .year(this.getYear())
                .build();
    }

    public EducationEntity(JSONObject jsonObject, EducationType type, ApplicantEntity applicant) throws JSONException {
        this.type = type;
        this.name = jsonObject.getString("name");
        this.nameId = jsonObject.getString("name_id");
        this.year = jsonObject.getInt("year");
        this.result = jsonObject.getString("result");
        this.resultId = jsonObject.getString("result_id");
        this.organization = jsonObject.getString("organization");
        this.organizationId = jsonObject.getString("organization_id");
        this.applicant = applicant;
    }
}
