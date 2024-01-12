package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.AreaType;
import com.example.course_paper_backend.model.Experience;
import jakarta.persistence.*;
import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "experiences")
public class ExperienceEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "company")
    private String company;
    @Column(name = "company_id")
    private String companyId;
    @Column(name = "company_url")
    private String companyUrl;
    @Column(name = "description")
    private String description;
    // В БД хранится как строка с разделителем ','
    @Column(name = "industries", nullable = false)
    private String industries;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "start_date")
    private Date start;
    @Column(name = "end_date")
    private Date end;
    @OneToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private AreaCitiEntity area;
    @ManyToOne
    @JoinColumn(name = "resume_id", referencedColumnName = "id", nullable = false)
    private ResumeEntity resume;

    public Experience toModel() {
        return new Experience().toBuilder()
                .id(this.getId())
                .area(this.getArea().toModel())
                .company(this.getCompany())
                .companyId(this.getCompanyId())
                .companyUrl(this.getCompanyUrl())
                .description(this.getDescription())
                .position(this.getPosition())
                .start(this.getStart())
                .end(this.getEnd())
                .industries(this.getIndustries().split(","))
                .build();
    }

    public ExperienceEntity(JSONObject jsonObject, ResumeEntity resume, ApplicantEntity applicant, SimpleDateFormat dateFormat) throws JSONException, ParseException {
        this.id = jsonObject.getLong("id");
        this.company = jsonObject.optString("company");
        this.companyId = jsonObject.optString("company_id");
        this.companyUrl = jsonObject.optString("company_url");
        this.description = jsonObject.optString("description");
        this.industries = jsonObject.getString("industries");
        this.position = jsonObject.getString("position");
        this.start = dateFormat.parse(jsonObject.optString("start"));
        this.end = dateFormat.parse(jsonObject.optString("end"));
        this.area = new AreaCitiEntity(jsonObject.getJSONObject("area"), AreaType.EXPERIENCE, applicant);
        this.resume = resume;
    }
}
