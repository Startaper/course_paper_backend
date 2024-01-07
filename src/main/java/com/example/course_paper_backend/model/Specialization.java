package com.example.course_paper_backend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Specialization {

    private String id;
    private String name;
    private boolean laboring;
    private Integer profAreaId;
    private String profAreaName;

}
