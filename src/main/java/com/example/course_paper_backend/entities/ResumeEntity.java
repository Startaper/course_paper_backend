package com.example.course_paper_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ResumeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private int age;
    private String title;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private Date createdAt;
    private Date updatedAt;
    private String skills;
    private int salary;

}
