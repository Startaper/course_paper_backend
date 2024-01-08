package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ResumeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResumeRepo extends CrudRepository<ResumeEntity, UUID> {

    boolean existsByExternalId(String externalId);

}
