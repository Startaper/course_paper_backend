package com.example.course_paper_backend.security;

import com.example.course_paper_backend.entities.UserEntity;
import com.example.course_paper_backend.security.jwt.JwtUserFactory;
import com.example.course_paper_backend.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServices implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с указанным username: " + username + " не найден!");
        }

        return JwtUserFactory.create(user);
    }

}
