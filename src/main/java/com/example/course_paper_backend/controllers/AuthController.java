package com.example.course_paper_backend.controllers;

import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.exceptions.AlreadyExistsException;
import com.example.course_paper_backend.exceptions.InvalidFieldsException;
import com.example.course_paper_backend.model.ResponseV1;
import com.example.course_paper_backend.security.jwt.JwtTokenProvider;
import com.example.course_paper_backend.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Класс-контроллер регистрации и авторизации в интерфейсе API
 */
@RestController
@RequestMapping("/")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    /**
     * Метод авторизации
     *
     * @param user UserEntity
     * @return ResponseEntity
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserEntity user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            user = userService.findByUsername(user.getUsername());
            return ResponseEntity.ok(new ResponseV1().toBuilder()
                    .token(jwtTokenProvider.createToken(user.getUsername()))
                    .isAdmin(user.isAdmin())
                    .build());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    /**
     * Метод регистрации
     *
     * @param user    UserEntity
     * @param isAdmin Boolean
     * @return ResponseEntity
     * @throws InvalidFieldsException если в передаваемых данных найдены невалидные или не заполненные обязательные поля
     * @throws AlreadyExistsException если при сохранении одного и то же объекта более 1 раза возникает ошибка
     */
    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody UserEntity user, @RequestParam(required = false) boolean isAdmin) throws InvalidFieldsException, AlreadyExistsException {
        user = userService.register(user, isAdmin);
        return ResponseEntity.ok(new ResponseV1().toBuilder()
                .token(jwtTokenProvider.createToken(user.getUsername()))
                .isAdmin(user.isAdmin())
                .build());
    }

}
