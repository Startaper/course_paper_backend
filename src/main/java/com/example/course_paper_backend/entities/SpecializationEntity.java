package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.model.Specialization;
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
@Table(name = "specialisations")
public class SpecializationEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "laboring", nullable = false)
    private boolean laboring;
    @Column(name = "prof_area_id", nullable = false)
    private int profAreaId;
    @Column(name = "prof_area_name", nullable = false)
    private String profAreaName;
    @ManyToOne
    @JoinColumn(name = "resume_id", referencedColumnName = "id", nullable = false)
    private ResumeEntity resume;

    public Specialization toModel(){
        return new Specialization().toBuilder()
                .id(this.getId())
                .name(this.getName())
                .laboring(this.isLaboring())
                .profAreaId(this.getProfAreaId())
                .profAreaName(this.getProfAreaName())
                .build();
    }

    public SpecializationEntity(JSONObject jsonObject, ResumeEntity resume) throws JSONException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.laboring = jsonObject.getBoolean("laboring");
        this.profAreaId = jsonObject.getInt("profarea_id");
        this.profAreaName = jsonObject.getString("profarea_name");
        this.resume = resume;
    }
}
