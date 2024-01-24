package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.entities.RoleEntity;
import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.enums.UserStatus;
import com.example.course_paper_backend.exceptions.AlreadyExistsException;
import com.example.course_paper_backend.exceptions.InvalidFieldsException;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.User;
import com.example.course_paper_backend.repositories.RoleRepository;
import com.example.course_paper_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
//    private final MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
//        this.mailService = mailService;
    }

    public UserEntity register(UserEntity user, boolean isAdmin) throws InvalidFieldsException, AlreadyExistsException {
        List<RoleEntity> userRoles = new ArrayList<>();
        if (isAdmin) {
            userRoles.add(roleRepository.findByName("ROLE_ADMIN"));
        } else {
            userRoles.add(roleRepository.findByName("ROLE_USER"));
        }

        checkNewUser(user);
        String username = user.getEmail().substring(0, user.getEmail().indexOf("@"));

        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyExistsException("Пользователь с указынным username: " + username + " уже есть в системе!");
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

    public List<UserEntity> addAll(List<User> users) throws InvalidFieldsException, AlreadyExistsException {
        List<UserEntity> userEntities = new ArrayList<>();
        for (User user : users) {
            userEntities.add(add(user));
        }
        return userEntities;
    }

    public UserEntity add(User user) throws InvalidFieldsException, AlreadyExistsException {
        UserEntity userEntity = new UserEntity();
        List<RoleEntity> userRoles = new ArrayList<>();
        if (user.isAdmin()) {
            userRoles.add(roleRepository.findByName("ROLE_ADMIN"));
        } else {
            userRoles.add(roleRepository.findByName("ROLE_USER"));
        }

        checkNewUser(user);
        String username = user.getEmail().substring(0, user.getEmail().indexOf("@"));

        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyExistsException("Пользователь с указынным username: " + username + " уже есть в системе!");
        }

        String generatedPassword = String.valueOf(UUID.randomUUID()).substring(0, 8);

        userEntity.setLastName(user.getLastName());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setMiddleName(user.getMiddleName());
        userEntity.setEmail(user.getEmail());
        userEntity.setRoles(userRoles);
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(generatedPassword));
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCreatedAt(new Date());
        userEntity.setUpdatedAt(new Date());

//        Отправка на указанную почту логина и пароля пользователя.

        return userRepository.save(userEntity);
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

    public UserEntity update(User user, Long id) throws InvalidFieldsException, UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!"));

        boolean result = false;
        if (!userEntity.getLastName().equals(user.getLastName())) {
            userEntity.setLastName(user.getLastName());
            result = true;
        }
        if (!userEntity.getFirstName().equals(user.getFirstName())) {
            userEntity.setFirstName(user.getFirstName());
            result = true;
        }
        if (userEntity.getMiddleName() == null || !userEntity.getMiddleName().equals(user.getMiddleName())) {
            userEntity.setMiddleName(user.getMiddleName());
            result = true;
        }
        if (!userEntity.getStatus().equals(user.getStatus())) {
            userEntity.setStatus(user.getStatus());
            result = true;
        }
        if (userEntity.isAdmin() != user.isAdmin()) {
            List<RoleEntity> roles = userEntity.getRoles();
            if (user.isAdmin()) {
                roles.add(roleRepository.findByName("ROLE_ADMIN"));
            } else {
                roles.remove(roleRepository.findByName("ROLE_ADMIN"));
            }
            userEntity.setRoles(roles);
            result = true;
        }

        if (result) {
            return userRepository.save(userEntity);
        } else {
            throw new InvalidFieldsException("Нет данных для обновления");
        }
    }

    public UserEntity findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным id не найден!"));
    }

    public void delete(Long id) throws NotFoundException {
        UserEntity user = findById(id);
        userRepository.delete(user);
    }

    private void checkNewUser(UserEntity user) throws InvalidFieldsException {
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new InvalidFieldsException("Пароль не может быть пустым!");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidFieldsException("Email не может быть пустым!");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new InvalidFieldsException("Фамилия не может быть пустым!");
        }
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new InvalidFieldsException("Имя не может быть пустым!");
        }
    }

    private void checkNewUser(User user) throws InvalidFieldsException {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidFieldsException("Email не может быть пустым!");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new InvalidFieldsException("Фамилия не может быть пустым!");
        }
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new InvalidFieldsException("Имя не может быть пустым!");
        }
    }

}
