package com.example.course_paper_backend.model;

import com.example.course_paper_backend.enums.SiteType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Site {

    private Long id;
    private SiteType type;
    private String url;

}
