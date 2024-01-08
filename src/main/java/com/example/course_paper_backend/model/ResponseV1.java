package com.example.course_paper_backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseV1 {

    private int errorCode;
    private String message;
    private List<Resume> resumes;
    private int count;
}
