package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.*;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.Resume;
import com.example.course_paper_backend.repositories.ResumeRepo;
import com.example.course_paper_backend.services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MainServiceImpl implements BasicService<Resume, ResumeEntity> {

    private final ResumeRepo resumeRepo;

    @Autowired
    public MainServiceImpl(ResumeRepo resumeRepo) {
        this.resumeRepo = resumeRepo;
    }

    @Override
    public ResumeEntity get(UUID resumeId) throws NotFoundException {
        return resumeRepo.findById(resumeId)
                .orElseThrow(() -> new NotFoundException("Резюме с указанным id не найден!"));
    }

    public List<ResumeEntity> getAllByFilter(ResumeStatus status, String areaName, Gender gender, TravelTimeType travelTimeType,
                                             EducationLevel educationLevel, BusinessTripReadinessType businessTripReadinessType,
                                             String ageStart, String ageEnd, String salaryStart, String salaryEnd) {
        List<ResumeEntity> result = new ArrayList<>();
        resumeRepo.findAll().forEach(result::add);

        if (status != null) {
            result.retainAll(resumeRepo.findAllByStatus(status));
        }
        if (gender != null) {
            result.retainAll(resumeRepo.findAllByApplicant_Gender(gender));
        }
        if (travelTimeType != null) {
            result.retainAll(resumeRepo.findAllByTravelTime(travelTimeType));
        }
        if (businessTripReadinessType != null) {
            result.retainAll(resumeRepo.findAllByBusinessTripReadiness(businessTripReadinessType));
        }
        if (educationLevel != null) {
            result.retainAll(resumeRepo.findAllByApplicant_EducationLevel(educationLevel));
        }
        if (areaName != null && !areaName.isBlank()) {
            result.retainAll(resumeRepo.findAllByApplicant_Area(areaName));
        }

        int start = 0;
        int end = Integer.MAX_VALUE;

        // Age between start, end
        if (ageStart != null && !ageStart.isBlank()) {
            start = Integer.parseInt(ageStart);
        }
        if (ageEnd != null && !ageEnd.isBlank()) {
            end = Integer.parseInt(ageEnd);
        }
        result.retainAll(resumeRepo.findAllByApplicant_AgeBetween(start, end));

        // Salary between start, end
        start = 0;
        end = Integer.MAX_VALUE;
        if (salaryStart != null && !salaryStart.isBlank()) {
            start = Integer.parseInt(salaryStart);
        }
        if (salaryEnd != null && !salaryEnd.isBlank()) {
            end = Integer.parseInt(salaryEnd);
        }
        result.retainAll(resumeRepo.findAllBySalaryBetween(start, end));

        return result;
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
    public ResumeEntity add(Resume model) {
        return null;
    }

}
