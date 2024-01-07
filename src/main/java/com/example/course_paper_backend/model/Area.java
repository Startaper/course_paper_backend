package com.example.course_paper_backend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Area {

    private Long id;
    private String url;
    private String name;

}
