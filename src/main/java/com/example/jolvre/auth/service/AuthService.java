package com.example.jolvre.auth.service;

import com.example.jolvre.auth.dto.VerificationStudentDTO.StudentVerificationApiFormat;
import com.example.jolvre.auth.dto.VerificationStudentDTO.StudentVerificationApiFormat.StudentVerificationApiFormatBuilder;
import com.example.jolvre.auth.dto.VerificationStudentDTO.StudentVerificationRequest;
import com.example.jolvre.auth.dto.VerificationStudentDTO.StudentVerificationResponse;
import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${verify.student.apiKey}")
    private String apiKey;

    private final UserRepository userRepository;
    private final WebClient webClient;

    public void sendStudentVerificationEmail(StudentVerificationRequest request) {
        log.info("[USER] : 대학생 인증 진입");

        StudentVerificationApiFormatBuilder format = StudentVerificationApiFormat.builder().key(apiKey)
                .request(request);

        webClient.post()
                .uri(VERIFY_STUDENT_API_URI + VERIFY_CODE_CALL)
                .body(BodyInserters.fromValue(format))
                .retrieve()
                .bodyToMono(StudentVerificationResponse.class)
                .block();
    }

//    @Transactional
//    public VerifyStudentByEmailResponse checkVerificationStudentEmail(
//            VerifyStudentByEmailRequest request, User user) {
//
//        VerifyStudentByEmailResponse response = webClient.post()
//                .uri(VERIFY_STUDENT_API_URI + VERIFY_EMAIL)
//                .body(BodyInserters.fromValue(request))
//                .retrieve()
//                .bodyToMono(VerifyStudentByEmailResponse.class)
//                .block();
//
//        if (Objects.requireNonNull(response).isSuccess()) {
//            updateAuthorize(user);
//            log.info("[USER] : 대학생 인증 성공");
//        }
//
//        return response;
//    }

    @Transactional
    public void updateAuthorize(User user) {

        log.info("[USER] : 유저 권한 변경 {} -> {}", user.getRole().getKey(), Role.STUDENT.getKey());

        User updaeteUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        updaeteUser.authorizeStudent();

        userRepository.save(updaeteUser);
    }
}
