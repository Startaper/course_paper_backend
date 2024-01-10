package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.EducationLevel;
import com.example.course_paper_backend.enums.ResumeStatus;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.Resume;
import com.example.course_paper_backend.repositories.ResumeRepo;
import com.example.course_paper_backend.services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<ResumeEntity> getAll() {
        List<ResumeEntity> resumes = new ArrayList<>();
        resumeRepo.findAll().forEach(resumes::add);
        return resumes;
    }

    public List<ResumeEntity> getAllByFilter_Var1(ResumeStatus status, String areaName, EducationLevel educationLevel,
                                             int ageStart, int ageEnd, int salaryStart, int salaryEnd) {
        return resumeRepo.findAllByStatusOrApplicant_Area_NameOrApplicant_EducationLevelOrApplicant_AgeBetweenOrSalaryBetween(
                status, areaName, educationLevel, ageStart, ageEnd, salaryStart, salaryEnd
        );
    }

    public List<ResumeEntity> getAllByFilter_Var2(Map<String, String> filters) {
        ResumeStatus status = ResumeStatus.valueOf(filters.get("status"));
        EducationLevel educationLevel = EducationLevel.valueOf(filters.get("educationLevel"));

        return resumeRepo.findAllByStatusOrApplicant_Area_NameOrApplicant_EducationLevelOrApplicant_AgeBetweenOrSalaryBetween(
                status, filters.get("areaName"), educationLevel,
                Integer.parseInt(filters.get("ageStart")), Integer.parseInt(filters.get("ageEnd")),
                Integer.parseInt(filters.get("salaryStart")), Integer.parseInt(filters.get("salaryEnd"))
        );
    }

    public List<ResumeEntity> getAllByFilter_Var3(Map<String, String> filters) {
        List<ResumeEntity> result = resumeRepo.findAllByStatus(ResumeStatus.valueOf(filters.get("status")));
        if (filters.containsKey("educationLevel") && filters.get("educationLevel") != null) {
            result.retainAll(resumeRepo.findAllByApplicant_EducationLevel(EducationLevel.valueOf(filters.get("educationLevel"))));
        }
        if (filters.containsKey("areaName") && filters.get("areaName") != null) {
            result.retainAll(resumeRepo.findAllByApplicant_Area_Name(filters.get("areaName")));
        }

        int start = 0;
        int end = Integer.MAX_VALUE;

        // Age between start, end
        if (filters.containsKey("ageStart") && filters.get("ageStart") != null) {
            start = Integer.parseInt(filters.get("ageStart"));
        }
        if (filters.containsKey("ageEnd") && filters.get("ageEnd") != null) {
            end = Integer.parseInt(filters.get("ageEnd"));
        }
        result.retainAll(resumeRepo.findAllByApplicant_AgeBetween(start, end));

        // Salary between start, end
        start = 0;
        end = Integer.MAX_VALUE;
        if (filters.containsKey("salaryStart") && filters.get("salaryStart") != null) {
            start = Integer.parseInt(filters.get("salaryStart"));
        }
        if (filters.containsKey("salaryEnd") && filters.get("salaryEnd") != null) {
            end = Integer.parseInt(filters.get("salaryEnd"));
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
