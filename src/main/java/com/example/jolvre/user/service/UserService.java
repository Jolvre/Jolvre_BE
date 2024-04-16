package com.example.jolvre.user.service;

import com.example.jolvre.auth.login.dto.UserSignUpDTO;
import com.example.jolvre.user.dto.VerifyStudentDTO.VerifyEmailSendRequest;
import com.example.jolvre.user.dto.VerifyStudentDTO.VerifyEmailSendResponse;
import com.example.jolvre.user.dto.VerifyStudentDTO.VerifyStudentByEmailRequest;
import com.example.jolvre.user.dto.VerifyStudentDTO.VerifyStudentByEmailResponse;
import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    private final String VERIFY_STUDENT_API_URI = "https://univcert.com/api/v1/";
    private final String VERIFY_CODE_CALL = "/certify";
    private final String VERIFY_EMAIL = "/certifycode";

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

        log.info("[AUTH] : 회원가입 성공");

        userRepository.save(user);
    }

    // 더티체킹 업데이트
    public User updateAuthorize(User user) {

        log.info("[USER] : 유저 권한 변경 {} -> {}", user.getRole().getKey(), Role.STUDENT.getKey());

        User updaeteUser = entityManager.find(User.class, user.getId());

        updaeteUser.authorizeStudent();

        return updaeteUser;
    }

    public VerifyEmailSendResponse verifyStudentCall(VerifyEmailSendRequest request) {
        WebClient client = WebClient.create(VERIFY_STUDENT_API_URI);

        log.info("[USER] : 대학생 인증 진입");

        try {

            VerifyEmailSendResponse response = client.post()
                    .uri(VERIFY_CODE_CALL)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(VerifyEmailSendResponse.class)
                    .block();

            log.info("[USER] : 대학생 인증 메일 전송 성공");

            return response;
        } catch (WebClientResponseException e) {

            log.info("[USER] : 대학생 인증 메일 전송 실패");

            return new VerifyEmailSendResponse(false, 400, e.getMessage());
        }
    }

    public VerifyStudentByEmailResponse verifyStudentByEmail(
            VerifyStudentByEmailRequest request, User user) {

        WebClient client = WebClient.create(VERIFY_STUDENT_API_URI);

        VerifyStudentByEmailResponse response = client.post()
                .uri(VERIFY_EMAIL)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(VerifyStudentByEmailResponse.class)
                .block();

        if (response.isSuccess()) {
            updateAuthorize(user);
            log.info("[USER] : 대학생 인증 성공");
        }
        return response;

    }
}
