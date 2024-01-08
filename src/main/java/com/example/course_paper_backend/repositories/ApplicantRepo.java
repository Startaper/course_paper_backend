package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ApplicantEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicantRepo extends CrudRepository<ApplicantEntity, UUID> {

    ApplicantEntity findByExternalId(String externalId);

}
