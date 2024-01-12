package com.example.course_paper_backend.controllers;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.*;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.ResponseV1;
import com.example.course_paper_backend.model.Resume;
import com.example.course_paper_backend.services.impl.MainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/resumes")
public class MainController {

    private final MainServiceImpl service;

    @Autowired
    public MainController(MainServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") UUID id) throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(service.get(id).toModel()))
                .build());
    }

    @GetMapping("/")
    public ResponseEntity getAllByFilter(@RequestParam(value = "status", required = false) ResumeStatus status,
                                         @RequestParam(value = "areaName", required = false) String areaName,
                                         @RequestParam(value = "gender", required = false) Gender gender,
                                         @RequestParam(value = "travelTime", required = false) TravelTimeType travelTimeType,
                                         @RequestParam(value = "educationLevel", required = false) EducationLevel educationLevel,
                                         @RequestParam(value = "businessTripReadiness", required = false) BusinessTripReadinessType businessTripReadinessType,
                                         @RequestParam(value = "ageStart", required = false) String ageStart,
                                         @RequestParam(value = "ageEnd", required = false) String ageEnd,
                                         @RequestParam(value = "salaryStart", required = false) String salaryStart,
                                         @RequestParam(value = "salaryEnd", required = false) String salaryEnd) {
        List<Resume> resumes = service.getAllByFilter(status, areaName, gender, travelTimeType, educationLevel, businessTripReadinessType, ageStart, ageEnd, salaryStart, salaryEnd).stream()
                .map(ResumeEntity::toModel)
                .toList();
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .resumes(resumes)
                .count(resumes.size())
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") UUID id, @RequestParam(name = "status") ResumeStatus status)
            throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(service.updateStatus(id, status).toModel()))
                .build());
    }

}
