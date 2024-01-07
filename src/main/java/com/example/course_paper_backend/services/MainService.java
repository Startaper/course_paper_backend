package com.example.course_paper_backend.services;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.ResumeStatus;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.Resume;
import com.example.course_paper_backend.repositories.ResumeRepo;
import com.example.course_paper_backend.services.impl.BasicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MainService implements BasicServiceImpl<Resume, ResumeEntity> {

    private final ResumeRepo resumeRepo;

    @Autowired
    public MainService(ResumeRepo resumeRepo) {
        this.resumeRepo = resumeRepo;
    }

    @Override
    public ResumeEntity get(UUID resumeId) throws NotFoundException {
        return resumeRepo.findById(resumeId)
                .orElseThrow(() -> new NotFoundException("Резюме с указанным id не найден!"));
    }

    public List<ResumeEntity> getAll() {
        List<ResumeEntity> resumes = new ArrayList<>();
        resumeRepo.findAll().forEach(resumes::add);
        return resumes;
    }

    @Override
    public ResumeEntity updateStatus(UUID resumeId, ResumeStatus status) throws NotFoundException {
        ResumeEntity resumeEntity = resumeRepo.findById(resumeId)
                .orElseThrow(() -> new NotFoundException("Резюме с указанным id не найден!"));
        resumeEntity.setStatus(status);
        return resumeRepo.save(resumeEntity);
    }

    @Override
    @Deprecated
    public void delete(UUID id) {

    }

    @Override
    @Deprecated
    public ResumeEntity add(Resume model) {
        return null;
    }

}
