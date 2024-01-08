package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.RecommendationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepo extends CrudRepository<RecommendationEntity, Long> {
}
