package com.example.course_paper_backend.repositories;

import com.example.course_paper_backend.entities.CertificateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepo extends CrudRepository<CertificateEntity, Long> {
}
