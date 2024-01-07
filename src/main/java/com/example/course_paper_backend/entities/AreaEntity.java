package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.AreaType;
import com.example.course_paper_backend.model.Area;
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
@Table(name = "areas")
public class AreaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AreaType type;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "url", nullable = false)
    private String url;
    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false)
    private ApplicantEntity applicant;

    public Area toModel() {
        return new Area().toBuilder()
                .id(this.getId())
                .url(this.getUrl())
                .name(this.getName())
                .build();
    }

    public AreaEntity(JSONObject jsonObject, AreaType areaType, ApplicantEntity applicant) throws JSONException {
        this.id = jsonObject.getLong("id");
        this.type = areaType;
        this.name = jsonObject.getString("name");
        this.url = jsonObject.getString("url");
        this.applicant = applicant;
    }
}
