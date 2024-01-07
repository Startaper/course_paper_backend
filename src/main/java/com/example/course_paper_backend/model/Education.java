package com.example.course_paper_backend.model;

import com.example.course_paper_backend.enums.EducationType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Education {

    private Long id;
    private EducationType type;
    private String name;
    private String nameId;
    //  При значениях "ADDITIONAL", "ATTESTATION" в поле type, это поле обязательно для заполнения.
    private String organization;
    private String organizationId;
    private String result;
    private String resultId;
    private int year;

}
