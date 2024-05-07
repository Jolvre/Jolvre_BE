package com.example.jolvre.auth.service;

import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyEmailSendRequest;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyEmailSendResponse;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyStudentByEmailRequest;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyStudentByEmailResponse;
import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final String VERIFY_STUDENT_API_URI = "https://univcert.com/api/v1/";
    private final String VERIFY_CODE_CALL = "/certify";
    private final String VERIFY_EMAIL = "/certifycode";

    private final UserRepository userRepository;

    @Transactional
    public VerifyEmailSendResponse verifyStudentCall(VerifyEmailSendRequest request) {
        WebClient client = WebClient.create(VERIFY_STUDENT_API_URI);

        log.info("[USER] : 대학생 인증 진입");

        VerifyEmailSendResponse response = client.post()
                .uri(VERIFY_CODE_CALL)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(VerifyEmailSendResponse.class)
                .block();

        log.info("[USER] : 대학생 인증 메일 전송 성공");

        return response;

    }

    @Transactional
    public VerifyStudentByEmailResponse verifyStudentByEmail(
            VerifyStudentByEmailRequest request, User user) {

        WebClient client = WebClient.create(VERIFY_STUDENT_API_URI);

        VerifyStudentByEmailResponse response = client.post()
                .uri(VERIFY_EMAIL)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(VerifyStudentByEmailResponse.class)
                .block();

        if (Objects.requireNonNull(response).isSuccess()) {
            updateAuthorize(user);
            log.info("[USER] : 대학생 인증 성공");
        }

        return response;
    }

    @Transactional
    public void updateAuthorize(User user) {

        log.info("[USER] : 유저 권한 변경 {} -> {}", user.getRole().getKey(), Role.STUDENT.getKey());

        User updaeteUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        updaeteUser.authorizeStudent();

        userRepository.save(updaeteUser);
    }
}
