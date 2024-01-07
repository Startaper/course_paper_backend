package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.LevelLanguage;
import com.example.course_paper_backend.model.Language;
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
@Table(name = "language")
public class LanguageEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LevelLanguage level;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false)
    private ApplicantEntity applicant;

    public Language toModel() {
        return Language.builder()
                .id(this.getId())
                .level(this.getLevel())
                .name(this.getName())
                .build();
    }

    public LanguageEntity(JSONObject jsonObject, ApplicantEntity applicant) throws JSONException {
        this.id = jsonObject.getString("id");
        this.level = LevelLanguage.valueOf(jsonObject.getJSONObject("level").getString("id"));
        this.name = jsonObject.getString("name");
        this.applicant = applicant;
    }
}
