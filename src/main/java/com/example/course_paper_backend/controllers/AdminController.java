package com.example.course_paper_backend.controllers;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.ResumeStatus;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.ResponseV1;
import com.example.course_paper_backend.services.AdminService;
import com.example.course_paper_backend.services.MainService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/resumes")
public class AdminController {

    private final AdminService adminService;
    private final MainService mainService;

    @Autowired
    public AdminController(AdminService adminService, MainService mainService) {
        this.adminService = adminService;
        this.mainService = mainService;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") UUID id) throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(mainService.get(id)))
                .build());
    }

    @GetMapping("/")
    public ResponseEntity getAll() {
        List<ResumeEntity> resumeEntities = mainService.getAll();
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .resumes(Collections.singletonList(resumeEntities))
                .count(resumeEntities.size())
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") UUID id,
                                       @RequestParam(name = "status") ResumeStatus status) throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(mainService.updateStatus(id, status)))
                .build());
    }

    @PostMapping("/")
    public ResponseEntity addAll(@RequestBody JSONArray jsonArray) throws JSONException, ParseException {
        List<ResumeEntity> resumes = adminService.addAll(jsonArray);
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .resumes(Collections.singletonList(resumes))
                .count(resumes.size())
                .build());
    }

}
