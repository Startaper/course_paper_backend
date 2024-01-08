package com.example.course_paper_backend.controllers;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.ResumeStatus;
import com.example.course_paper_backend.exceptions.AlreadyExistsException;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.ResponseV1;
import com.example.course_paper_backend.model.Resume;
import com.example.course_paper_backend.services.impl.AdminServiceImpl;
import com.example.course_paper_backend.services.impl.MainServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/admin/resumes")
public class AdminController {

    private final AdminServiceImpl adminService;
    private final MainServiceImpl mainService;

    @Autowired
    public AdminController(AdminServiceImpl adminService, MainServiceImpl mainService) {
        this.adminService = adminService;
        this.mainService = mainService;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") UUID id) throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(mainService.get(id).toModel()))
                .build());
    }

    @GetMapping("/")
    public ResponseEntity getAll() {
        List<Resume> resumes = mainService.getAll().stream()
                .map(ResumeEntity::toModel)
                .toList();
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .resumes(resumes)
                .count(resumes.size())
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") UUID id,
                                       @RequestParam(name = "status") ResumeStatus status) throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(mainService.updateStatus(id, status).toModel()))
                .build());
    }

    @PostMapping("/")
    public ResponseEntity addAll(@RequestBody String jsonArray) throws JSONException, ParseException, AlreadyExistsException {
        List<Resume> resumes = adminService.addAll(new JSONArray(jsonArray)).stream()
                .map(ResumeEntity::toModel)
                .toList();
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .resumes(resumes)
                .count(resumes.size())
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) throws NotFoundException {
        adminService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/")
    public ResponseEntity deleteAll() {
        adminService.deleteAll();
        return ResponseEntity.ok().build();
    }

}
