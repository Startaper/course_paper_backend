package com.example.course_paper_backend.controllers;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.ResumeStatus;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.ResponseV1;
import com.example.course_paper_backend.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes")
public class MainController {

    private final MainService service;

    @Autowired
    public MainController(MainService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") UUID id) throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(service.get(id)))
                .build());
    }

    @GetMapping("/")
    public ResponseEntity getAll() {
        List<ResumeEntity> resumeEntities = service.getAll();
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .resumes(Collections.singletonList(resumeEntities))
                .count(resumeEntities.size())
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") UUID id, @RequestParam(name = "status") ResumeStatus status) throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(service.updateStatus(id, status)))
                .build());
    }

}
