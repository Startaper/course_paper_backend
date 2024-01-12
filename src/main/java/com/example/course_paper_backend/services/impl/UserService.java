package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.entities.RoleEntity;
import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.enums.UserStatus;
import com.example.course_paper_backend.exceptions.AlreadyExistsException;
import com.example.course_paper_backend.exceptions.InvalidFieldsException;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.repositories.RoleRepository;
import com.example.course_paper_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity register(UserEntity user, boolean isAdmin) throws InvalidFieldsException, AlreadyExistsException {
        List<RoleEntity> userRoles = new ArrayList<>();
        if (isAdmin) {
            userRoles.add(roleRepository.findByName("ROLE_ADMIN"));
        } else {
            userRoles.add(roleRepository.findByName("ROLE_USER"));
        }

        String username = user.getEmail().substring(0, user.getEmail().indexOf("@"));

        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyExistsException("Пользователь с указынным username: " + username + " уже есть в системе!");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new InvalidFieldsException("Логин и (или) пароль не могут быть пустыми!");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new InvalidFieldsException("Фамилия не может быть пустым!");
        }
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new InvalidFieldsException("Имя не может быть пустым!");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidFieldsException("Email не может быть пустым!");
        }

        user.setRoles(userRoles);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        return userRepository.save(user);
    }

    public List<UserEntity> getAll() {
        List<UserEntity> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public List<RoleEntity> getUserRolesByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с указанным username: " + username + " не найден!");
        }

        return user.getRoles();
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным id не найден!"));
    }

    public void delete(Long id) throws NotFoundException {
        UserEntity user = findById(id);
        userRepository.delete(user);
    }

}
