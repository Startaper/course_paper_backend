package com.example.course_paper_backend.controllers;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.enums.*;
import com.example.course_paper_backend.exceptions.AlreadyExistsException;
import com.example.course_paper_backend.exceptions.InvalidFieldsException;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.ResponseV1;
import com.example.course_paper_backend.model.Resume;
import com.example.course_paper_backend.model.User;
import com.example.course_paper_backend.services.impl.AdminServiceImpl;
import com.example.course_paper_backend.services.impl.MainServiceImpl;
import com.example.course_paper_backend.services.impl.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

/**
 * Класс-контроллер для admin-интерфейса API
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl adminService;
    private final UserService userService;

    @Autowired
    public AdminController(AdminServiceImpl adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    /**
     * Метод передает запрос на следующий слой сервиса и возвращает список объектов, полученных в качества параметра и сохраненных в БД.
     *
     * @param jsonArray String
     * @return ResponseEntity
     * @throws JSONException          если при парсинге json возникает ошибка
     * @throws ParseException         если при парсинге дат возникает ошибка
     * @throws AlreadyExistsException если при сохранении одного и то же объекта более 1 раза возникает ошибка
     */
    @PostMapping("/api/resumes/")
    public ResponseEntity addAll(@RequestBody String jsonArray) throws JSONException, ParseException, AlreadyExistsException {
        List<Resume> resumes = adminService.addAll(new JSONArray(jsonArray)).stream()
                .map(ResumeEntity::toModel)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseV1().toBuilder()
                .resumes(resumes)
                .count(resumes.size())
                .build());
    }

    /**
     * Метод передает запрос на следующий слой сервиса для удаления объекта из БД по id
     *
     * @param id UUID
     * @return ResponseEntity
     * @throws NotFoundException если не удалось найти в БД объект по указанному id
     */
    @DeleteMapping("/api/resumes/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) throws NotFoundException {
        adminService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод передает запрос на следующий слой сервиса для удаления всех объектов из БД
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/api/resumes/")
    public ResponseEntity deleteAll() {
        adminService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/")
    public ResponseEntity getAllUsers() {
        List<UserEntity> users = userService.getAll();
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .users(users.stream()
                        .map(UserEntity::toModel)
                        .toList())
                .count(users.size())
                .build());
    }

    @PostMapping("/users/")
    public ResponseEntity addAllUsers(@RequestBody List<User> users) throws AlreadyExistsException, InvalidFieldsException {
        List<UserEntity> userEntities = userService.addAll(users);
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .users(userEntities.stream()
                        .map(UserEntity::toModel)
                        .toList())
                .count(userEntities.size())
                .build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) throws NotFoundException {
        userService.delete(id);
        return ResponseEntity.ok("Пользователь успешно удален");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable("id") Long id) throws InvalidFieldsException {
        return ResponseEntity.ok(userService.update(user, id).toModel());
    }

}
