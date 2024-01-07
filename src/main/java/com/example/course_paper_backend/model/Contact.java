package com.example.course_paper_backend.model;

import com.example.course_paper_backend.enums.ContactType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Contact {

    private Long id;
    private boolean verified;
    private boolean preferred;
    private ContactType type;
    private String value;
    private String comment;

}
