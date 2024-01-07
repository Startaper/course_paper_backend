package com.example.course_paper_backend.model;

import com.example.course_paper_backend.enums.CertificateType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Certificate {

    private Long id;
    private Date achievedAt;
    private String owner;
    private String title;
    private CertificateType type;
    private String url;

}
