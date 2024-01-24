package com.example.course_paper_backend.entities;

import com.example.course_paper_backend.enums.UserStatus;
import com.example.course_paper_backend.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "t_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<RoleEntity> roles;

    public boolean isAdmin() {
        List<String> rolesStr = this.getRoles().stream().map(RoleEntity::getName).toList();
        return rolesStr.contains("ROLE_ADMIN");
    }

    public User toModel() {
        return new User().toBuilder()
                .id(this.getId())
                .lastName(this.getLastName())
                .firstName(this.getFirstName())
                .middleName(this.getMiddleName())
                .email(this.getEmail())
                .status(this.getStatus())
                .admin(isAdmin())
                .build();
    }

}
