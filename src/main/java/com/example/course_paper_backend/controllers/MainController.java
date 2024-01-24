package com.example.course_paper_backend.controllers;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.*;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.ResponseV1;
import com.example.course_paper_backend.model.Resume;
import com.example.course_paper_backend.services.impl.MainServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Класс-контроллер основной части интерфейса API
 */
@RestController
@RequestMapping("/api/resumes")
public class MainController {

    private final MainServiceImpl service;

    @Autowired
    public MainController(MainServiceImpl service) {
        this.service = service;
    }

    /**
         * Метод передает запрос на следующий слой сервиса и возвращает объект по запрашиваемому id
     *
     * @param id UUID
     * @return ResponseEntity
     * @throws NotFoundException если не удалось найти в БД объект по указанному id
     */
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") UUID id) throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(service.get(id).toModel()))
                .build());
    }

    /**
     * Метод передает запрос на следующий слой сервиса и возвращает список объектов по запрошенным фильтрам.
     * Если не указанны фильтры, то возвращает все объекты из БД
     *
     * @param jsonString JSONObject
     * @return ResponseEntity
     * @throws JSONException
     */
    @PostMapping("/")
    public ResponseEntity getAllByFilter(@RequestBody(required = false) String jsonString) throws JSONException {
        List<Resume> resumes = service.getAllByFilter(jsonString).stream()
                .map(ResumeEntity::toModel)
                .toList();
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .resumes(resumes)
                .count(resumes.size())
                .build());
    }

    /**
     * Метод передает запрос на следующий слой сервиса и возвращает объект с обновленным статусом
     *
     * @param id UUID
     * @param status ResumeStatus
     * @return ResponseEntity
     * @throws NotFoundException если не удалось найти в БД объект по указанному id
     */
    @PutMapping("/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") UUID id, @RequestParam(name = "newStatus") ResumeStatus status)
            throws NotFoundException {
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .count(1)
                .resumes(Collections.singletonList(service.updateStatus(id, status).toModel()))
                .build());
    }

}
