package com.example.course_paper_backend.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Experience {

    private Long id;
    private String area;
    private String company;
    private String companyId;
    private String companyUrl;
    private String description;
    private String position;
    private Date start;
    private Date end;
    // В БД хранится как строка с разделителем ','
    private String[] industries;

}
