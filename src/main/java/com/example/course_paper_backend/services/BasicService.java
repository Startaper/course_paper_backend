package com.example.course_paper_backend.services;

import com.example.course_paper_backend.enums.ResumeStatus;
import com.example.course_paper_backend.exceptions.InvalidFieldsException;
import com.example.course_paper_backend.exceptions.NotFoundException;

import java.util.UUID;

public interface BasicService<M, E> {

    E get(UUID id) throws InvalidFieldsException, NotFoundException;
    @Deprecated
    E add(M model);
    E updateStatus(UUID resumeId, ResumeStatus status) throws NotFoundException;

}
