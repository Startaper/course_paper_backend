package com.example.course_paper_backend.security.jwt;

import com.example.course_paper_backend.entities.RoleEntity;
import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUserDetails create(UserEntity user) {
        return new JwtUserDetails(
                user.getId(),
                user.getUsername(),
                user.getLastName(),
                user.getFirstName(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus().equals(UserStatus.ACTIVE),
                user.getUpdatedAt(),
                mapToGrantedAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
