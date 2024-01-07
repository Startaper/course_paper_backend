package com.example.course_paper_backend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Employment {

    private String id;
    private String name;

    public String getId() {
        return id.toUpperCase();
    }

}
