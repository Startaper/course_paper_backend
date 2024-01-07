package com.example.course_paper_backend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Recommendation {

    private Long id;
    private String contact;
    private String name;
    private String organization;
    private String position;

}
