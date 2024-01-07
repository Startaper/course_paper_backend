package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ResumeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepo extends CrudRepository<ResumeEntity, UUID> {

    List<ResumeEntity> findAllByApplicantId(UUID applicantId);
}
