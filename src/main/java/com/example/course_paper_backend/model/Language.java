package com.example.course_paper_backend.model;

import com.example.course_paper_backend.enums.LevelLanguage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Language {

    private String id;
    private String name;
    private LevelLanguage level;

}
