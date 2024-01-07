package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.model.PaidServices;
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
@Table(name = "paid_services")
public class PaidServicesEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "url", nullable = false)
    private String name;
    @Column(name = "active", nullable = false)
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "resume_id", referencedColumnName = "id", nullable = false)
    private ResumeEntity resume;

    public PaidServices toModel(){
        return new PaidServices().toBuilder()
                .id(this.getId())
                .name(this.getName())
                .active(this.isActive())
                .build();
    }

    public PaidServicesEntity(JSONObject jsonObject, ResumeEntity resume) throws JSONException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.active = jsonObject.getBoolean("active");
        this.resume = resume;
    }
}
