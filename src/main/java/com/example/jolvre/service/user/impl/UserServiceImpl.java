package com.example.jolvre.service.user.impl;

import com.example.jolvre.domain.user.Role;
import com.example.jolvre.domain.user.User;
import com.example.jolvre.domain.user.dto.UserSignUpDTO;
import com.example.jolvre.repository.user.UserRepository;
import com.example.jolvre.service.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Override
    public void signUp(UserSignUpDTO userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .age(userSignUpDto.getAge())
                .city(userSignUpDto.getCity())
                .school(userSignUpDto.getSchool())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    @Override // 더티체킹 업데이트
    public User update(User user) {
        User updaeteUser = entityManager.find(User.class, user.getId());

        user.setName("바보");

        return user;
    }

    @Override
    public User verifyStudent(User user) {
        return user;
    }
}
