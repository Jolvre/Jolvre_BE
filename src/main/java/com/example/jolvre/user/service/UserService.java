package com.example.jolvre.user.service;

import com.example.jolvre.auth.login.dto.UserSignUpDTO;
import com.example.jolvre.user.dto.VerifyStudentByEmailRequest;
import com.example.jolvre.user.dto.VerifyStudentByEmailResponse;
import com.example.jolvre.user.dto.VerifyStudentCallRequest;
import com.example.jolvre.user.dto.VerifyStudentCallResponse;
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
    private final WebClient webClient;
    private final String VERIFY_STUDENT_API_URI = "https://univcert.com/api/v1";

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

    // 더티체킹 업데이트
    public User updateAuthorize(User user) {
        User updaeteUser = entityManager.find(User.class, user.getId());

        updaeteUser.authorizeStudent();

        return updaeteUser;
    }

    public VerifyStudentCallResponse verifyStudentCall(VerifyStudentCallRequest verifyStudentRequestDTO) {
        WebClient client = WebClient.create("https://univcert.com/api/v1/");

        try {

            VerifyStudentCallResponse response = client.post()
                    .uri("/certify")
                    .body(BodyInserters.fromValue(verifyStudentRequestDTO))
                    .retrieve()
                    .bodyToMono(VerifyStudentCallResponse.class)
                    .block();

            log.info("aaaa ={} {} {}", response.getCode(), response.isSuccess(), response.getMessage());
            return response;
        } catch (WebClientResponseException e) {
            return new VerifyStudentCallResponse(false, 400, e.getMessage());
        }
    }

    public VerifyStudentByEmailResponse verifyStudentByEmail(
            VerifyStudentByEmailRequest verifyStudentByEmailRequest, User user) {

        WebClient client = WebClient.create("https://univcert.com/api/v1/");

        VerifyStudentByEmailResponse response = client.post()
                .uri("/certifycode")
                .body(BodyInserters.fromValue(verifyStudentByEmailRequest))
                .retrieve()
                .bodyToMono(VerifyStudentByEmailResponse.class)
                .block();

        log.info("aaaa ={} {} {}", response.getCode(), response.isSuccess(), response.getMessage());

        if (response.isSuccess()) {
            updateAuthorize(user);
        }

        return response;

    }
}
