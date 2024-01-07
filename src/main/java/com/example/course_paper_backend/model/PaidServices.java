package com.example.course_paper_backend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PaidServices {

    private String id;
    private String name;
    private boolean active;

}
