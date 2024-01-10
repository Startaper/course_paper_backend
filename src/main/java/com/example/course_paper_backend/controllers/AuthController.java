package com.example.course_paper_backend.controllers;

import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.exceptions.AlreadyExistsException;
import com.example.course_paper_backend.exceptions.InvalidFieldsException;
import com.example.course_paper_backend.security.jwt.JwtTokenProvider;
import com.example.course_paper_backend.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserEntity user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            user = userService.findByUsername(user.getUsername());
            return ResponseEntity.ok(jwtTokenProvider.createToken(user.getUsername()));
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody UserEntity user) throws InvalidFieldsException, AlreadyExistsException {
        user = userService.register(user);
        return ResponseEntity.ok(jwtTokenProvider.createToken(user.getUsername()));
    }

}
