package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.enums.ResumeStatus;
import com.example.course_paper_backend.exceptions.InvalidFieldsException;
import com.example.course_paper_backend.exceptions.NotFoundException;

import java.util.UUID;

public interface BasicServiceImpl<M, E> {

    E get(UUID id) throws InvalidFieldsException, NotFoundException;
    @Deprecated
    E add(M model);
    E updateStatus(UUID resumeId, ResumeStatus status) throws NotFoundException;
    void delete(UUID id) throws NotFoundException, InvalidFieldsException;

}
