package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ExperienceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepo extends CrudRepository<ExperienceEntity, Long> {
}
