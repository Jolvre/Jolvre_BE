package com.example.jolvre.service.user.impl;

import com.example.jolvre.domain.user.User;
import com.example.jolvre.repository.user.UserRepository;
import com.example.jolvre.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public User verifyStudent(User user) {
        return user;
    }
}
