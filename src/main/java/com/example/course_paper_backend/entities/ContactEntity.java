package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.ContactType;
import com.example.course_paper_backend.model.Contact;
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
@Table(name = "contacts")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ContactType type;
    @Column(name = "value", nullable = false)
    private String value;
    @Column(name = "comment")
    private String comment;
    @Column(name = "verified")
    private boolean verified;
    @Column(name = "preferred", nullable = false)
    private boolean preferred;
    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id", nullable = false)
    private ApplicantEntity applicant;

    public Contact toModel() {
        return new Contact().toBuilder()
                .id(this.getId())
                .type(this.getType())
                .preferred(this.isPreferred())
                .verified(this.isVerified())
                .comment(this.getComment())
                .value(this.getValue())
                .build();
    }

    public ContactEntity(JSONObject jsonObject, ApplicantEntity applicant) throws JSONException {
        this.type = ContactType.valueOf(jsonObject.getJSONObject("type").getString("id").toUpperCase());
        this.comment = jsonObject.optString("comment");
        this.verified = jsonObject.optBoolean("verified");
        this.preferred = jsonObject.getBoolean("preferred");
        this.applicant = applicant;

        if (this.getType() == ContactType.EMAIL) {
            this.value = jsonObject.getString("value");
        } else {
            this.value = jsonObject.getJSONObject("value").getString("formatted");
        }
    }
}
