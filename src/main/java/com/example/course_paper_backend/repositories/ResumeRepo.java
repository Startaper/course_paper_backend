package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.EducationLevel;
import com.example.course_paper_backend.enums.ResumeStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepo extends CrudRepository<ResumeEntity, UUID> {

    boolean existsByExternalId(String externalId);
    List<ResumeEntity> findAllByStatus(ResumeStatus status);
    List<ResumeEntity> findAllByApplicant_Area_Name(String areaName);
    List<ResumeEntity> findAllByApplicant_EducationLevel(EducationLevel educationLevel);
    List<ResumeEntity> findAllByApplicant_AgeBetween(int ageStart, int ageEnd);
    List<ResumeEntity> findAllBySalaryBetween(int salaryStart, int salaryEnd);
    List<ResumeEntity> findAllByStatusOrApplicant_Area_NameOrApplicant_EducationLevelOrApplicant_AgeBetweenOrSalaryBetween(
            ResumeStatus status,
            String areaName,
            EducationLevel educationLevel,
            int ageStart,
            int ageEnd,
            int salaryStart,
            int salaryEnd
    );

}
