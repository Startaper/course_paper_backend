package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepo extends CrudRepository<ResumeEntity, UUID> {

    boolean existsByExternalId(String externalId);
    @Query(value = "SELECT re FROM ResumeEntity re left join ApplicantEntity ae on re.applicant = ae " +
            "WHERE (:gender is null or ae.gender = :gender) " +
            "AND (:eduLevel is null or ae.educationLevel = :eduLevel) " +
            "AND (:status is null or re.status = :status) " +
            "AND (:travelTime is null or re.travelTime = :travelTime) " +
            "AND (:busTripRead is null or re.businessTripReadiness = :busTripRead) " +
            "AND (:areaName is null or ae.area = :areaName) " +
            "AND (:ageStart is null or ae.age >= :ageStart) " +
            "AND (:ageEnd is null or ae.age <= :ageEnd) " +
            "AND (:salaryStart is null or re.salary >= :salaryStart) " +
            "AND (:salaryEnd is null or re.salary <= :salaryEnd)")
    List<ResumeEntity> findAllByFilters(@Param("gender") Gender gender,
                                        @Param("eduLevel") EducationLevel eduLevel,
                                        @Param("status") ResumeStatus status,
                                        @Param("travelTime") TravelTimeType travelTime,
                                        @Param("busTripRead") BusinessTripReadinessType busTripRead,
                                        @Param("areaName") String areaName,
                                        @Param("ageStart") Integer ageStart,
                                        @Param("ageEnd") Integer ageEnd,
                                        @Param("salaryStart") Integer salaryStart,
                                        @Param("salaryEnd") Integer salaryEnd);
    @Query(value = "SELECT avg(salary) FROM ResumeEntity")
    Double avgSalary();
    @Query(value = "SELECT min(salary) FROM ResumeEntity")
    Integer minSalary();
    @Query(value = "SELECT max(salary) FROM ResumeEntity")
    Integer maxSalary();

}
