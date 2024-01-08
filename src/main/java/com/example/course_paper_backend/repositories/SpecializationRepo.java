package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.SpecializationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepo extends CrudRepository<SpecializationEntity, String> {
}
