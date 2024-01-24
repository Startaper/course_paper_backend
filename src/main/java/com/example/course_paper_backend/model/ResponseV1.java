package com.example.course_paper_backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseV1 {

    private int errorCode;
    private String message;
    private String token;
    private boolean isAdmin;
    private List<User> users;
    private List<Resume> resumes;
    private int count;

}
