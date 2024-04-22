package com.example.jolvre.user.service;

import com.example.jolvre.user.dto.DuplicationDTO.DuplicateEmailResponse;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateNicknameResponse;
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
}
