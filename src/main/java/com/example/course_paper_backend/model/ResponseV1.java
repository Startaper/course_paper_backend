package com.example.course_paper_backend.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ResponseV1 {

    private int errorCode;
    private String message;
    private List<Object> resumes;
    private int count;
}
