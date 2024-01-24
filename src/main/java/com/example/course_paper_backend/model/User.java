package com.example.course_paper_backend.model;

import com.example.course_paper_backend.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {

    private Long id;
    private UserStatus status;
    private String email;
    private String lastName;
    private String firstName;
    private String middleName;
    private boolean admin;

}
