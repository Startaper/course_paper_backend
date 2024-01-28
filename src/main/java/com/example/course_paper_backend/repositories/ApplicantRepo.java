package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ApplicantEntity;
import com.example.course_paper_backend.enums.EducationLevel;
import com.example.course_paper_backend.enums.Gender;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicantRepo extends CrudRepository<ApplicantEntity, UUID> {

    ApplicantEntity findByExternalId(String externalId);
    @Query(value = "SELECT avg(ae.age) FROM ApplicantEntity ae")
    Double avgAge();
    @Query(value = "SELECT min(ae.age) FROM ApplicantEntity ae")
    Integer minAge();
    @Query(value = "SELECT max(ae.age) FROM ApplicantEntity ae")
    Integer maxAge();

}
