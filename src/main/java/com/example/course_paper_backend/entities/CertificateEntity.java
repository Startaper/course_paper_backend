package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.CertificateType;
import com.example.course_paper_backend.model.Certificate;
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
@Table(name = "certificates")
public class CertificateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CertificateType type;
    @Column(name = "achieved_at", nullable = false)
    private Date achievedAt;
    @Column(name = "owner", nullable = false)
    private String owner;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "url", nullable = false)
    private String url;
    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false)
    private ApplicantEntity applicant;

    public Certificate toModel(){
        return new Certificate().toBuilder()
                .id(this.getId())
                .type(this.getType())
                .title(this.getTitle())
                .achievedAt(this.getAchievedAt())
                .owner(this.getOwner())
                .url(this.getUrl())
                .build();
    }

    public CertificateEntity(JSONObject jsonObject, SimpleDateFormat dateFormat, ApplicantEntity applicant) throws JSONException, ParseException {
        this.type = CertificateType.valueOf(jsonObject.getString("type").toUpperCase());
        this.achievedAt = dateFormat.parse(jsonObject.getString("achieved_at"));
        this.owner = jsonObject.getString("owner");
        this.title = jsonObject.getString("title");
        this.url = jsonObject.getString("url");
        this.applicant = applicant;
    }
}
