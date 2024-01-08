package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.ContactEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends CrudRepository<ContactEntity, Long> {
}
