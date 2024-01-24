package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepo extends CrudRepository<ResumeEntity, UUID> {

    boolean existsByExternalId(String externalId);
    List<ResumeEntity> findAllByStatus(ResumeStatus status);
    List<ResumeEntity> findAllByApplicant_Area(String areaName);
    List<ResumeEntity> findAllByTravelTime(TravelTimeType travelTime);
    List<ResumeEntity> findAllByBusinessTripReadiness(BusinessTripReadinessType businessTripReadinessType);
    List<ResumeEntity> findAllByApplicant_Gender(Gender gender);
    List<ResumeEntity> findAllByApplicant_EducationLevel(EducationLevel educationLevel);
    List<ResumeEntity> findAllByApplicant_AgeBetween(int ageStart, int ageEnd);
    List<ResumeEntity> findAllBySalaryBetween(int salaryStart, int salaryEnd);
    List<ResumeEntity> findAllBySkillSetContaining(String skillSet);

}
