package com.example.jolvre.user.service;

import com.example.jolvre.user.dto.DuplicationDTO.DuplicateEmailResponse;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateNicknameResponse;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import com.example.jolvre.user.dto.UserDTO.UserUpdateRequest;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public DuplicateEmailResponse checkDuplicateEmail(String email) {
        return new DuplicateEmailResponse(userRepository.existsByEmail(email));
    }

    public DuplicateNicknameResponse checkDuplicateNickname(String nickname) {
        return new DuplicateNicknameResponse(userRepository.existsByNickname(nickname));
    }

    public UserInfoResponse getUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        return UserInfoResponse.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .age(user.getAge())
                .city(user.getCity())
                .school(user.getSchool())
                .role(user.getRole())
                .imageUrl(user.getImageUrl())
                .build();
    }

    public void updateUser(long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        user.update(request);

        userRepository.save(user);
    }
}
