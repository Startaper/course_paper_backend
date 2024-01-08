package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.entities.RoleEntity;
import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.enums.UserStatus;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.repositories.RoleRepository;
import com.example.course_paper_backend.repositories.UserRepository;
import com.example.course_paper_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl {
//        implements UserService {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public UserEntity register(UserEntity user) {
//        List<RoleEntity> userRoles = Collections.singletonList(roleRepository.findByName("ROLE_USER"));
//
//        user.setRoles(userRoles);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setStatus(UserStatus.ACTIVE);
//
//        return userRepository.save(user);
//    }
//
//    @Override
//    public List<UserEntity> getAll() {
//        List<UserEntity> users = new ArrayList<>();
//        userRepository.findAll().forEach(users::add);
//        return users;
//    }
//
//    @Override
//    public UserEntity findByUsername(String username) throws NotFoundException {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new NotFoundException("Пользователь с указанным username не найден!"));
//
//    }
//
//    @Override
//    public UserEntity findById(Long id) throws NotFoundException {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Пользователь с указанным id не найден!"));
//    }
//
//    @Override
//    public void delete(Long id) throws NotFoundException {
//        UserEntity user = findById(id);
//        userRepository.delete(user);
//    }

}
