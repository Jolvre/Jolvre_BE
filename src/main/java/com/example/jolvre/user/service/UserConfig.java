package com.example.jolvre.user.service;

import com.example.jolvre.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final WebClient webClient;

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, passwordEncoder, entityManager, webClient);
    }
}
