package com.example.jolvre.user.service;

import com.example.jolvre.common.error.user.UserNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import com.example.jolvre.user.dto.UserDTO.UserUpdateRequest;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse getUserInfo(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return UserInfoResponse.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .age(user.getAge())
                .city(user.getCity())
                .school(user.getSchool())
                .role(user.getRole())
                .imageUrl(user.getImageUrl())
                .email(user.getEmail())
                .build();
    }

    public void updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (request.getImage() != null) {
            String updateImageUrl = s3Service.updateImage(request.getImage(), user.getImageUrl());
            user.updateImage(updateImageUrl);
        }

        user.update(request);

        userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(UserNotFoundException::new);
    }

    public void updatePassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.setPassword(password);
        user.passwordEncode(passwordEncoder);

        userRepository.save(user);
    }
}
