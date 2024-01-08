package com.example.course_paper_backend.services;

import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.exceptions.NotFoundException;

import java.util.List;

public interface UserService {

    UserEntity register(UserEntity user);
    List<UserEntity> getAll();
    UserEntity findByUsername(String username) throws NotFoundException;
    UserEntity findById(Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;

}
