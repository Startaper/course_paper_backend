package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.SiteType;
import com.example.course_paper_backend.model.Site;
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
@Table(name = "sites")
public class SiteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SiteType type;
    @Column(name = "url", nullable = false)
    private String url;
    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false)
    private ApplicantEntity applicant;

    public Site toModel() {
        return new Site().toBuilder()
                .id(this.getId())
                .type(this.getType())
                .url(this.getUrl())
                .build();
    }

    public SiteEntity(JSONObject jsonObject, ApplicantEntity applicant) throws JSONException {
        this.type = SiteType.valueOf(jsonObject.getJSONObject("type").getString("id").toUpperCase());
        this.url = jsonObject.getString("url");
        this.applicant = applicant;
    }
}
