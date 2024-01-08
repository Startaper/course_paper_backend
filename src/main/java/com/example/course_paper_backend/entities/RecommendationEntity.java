package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.model.Recommendation;
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
@Table(name = "recommendations")
public class RecommendationEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "organisation", nullable = false)
    private String organization;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "contact")
    private String contact;
    @ManyToOne
    @JoinColumn(name = "resume_id", referencedColumnName = "id", nullable = false)
    private ResumeEntity resume;

    public Recommendation toModel(){
        return new Recommendation().toBuilder()
                .id(this.getId())
                .name(this.getName())
                .organization(this.getOrganization())
                .position(this.getPosition())
                .contact(this.getContact())
                .build();
    }

    public RecommendationEntity(JSONObject jsonObject, ResumeEntity resume) throws JSONException {
        this.id = jsonObject.getLong("id");
        this.name = jsonObject.getString("name");
        this.organization = jsonObject.getString("organization");
        this.position = jsonObject.getString("position");
        this.contact = jsonObject.optString("contact");
        this.resume = resume;
    }
}
