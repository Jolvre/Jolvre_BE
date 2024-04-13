package com.example.jolvre.user.service;

import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.dto.UserSignUpDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentByEmailRequestDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentByEmailResponseDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentCallRequestDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentCallResponseDTO;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final WebClient webClient;
    private final String VERIFY_STUDENT_API_URI = "https://univcert.com/api/v1";

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
    public User updateAuthorize(User user) {
        User updaeteUser = entityManager.find(User.class, user.getId());

        updaeteUser.authorizeStudent();

        return updaeteUser;
    }

    @Override
    public VerifyStudentCallResponseDTO verifyStudentCall(VerifyStudentCallRequestDTO verifyStudentRequestDTO) {
        WebClient client = WebClient.create("https://univcert.com/api/v1/");

        try {

            VerifyStudentCallResponseDTO response = client.post()
                    .uri("/certify")
                    .body(BodyInserters.fromValue(verifyStudentRequestDTO))
                    .retrieve()
                    .bodyToMono(VerifyStudentCallResponseDTO.class)
                    .block();

            log.info("aaaa ={} {} {}", response.getCode(), response.isSuccess(), response.getMessage());
            return response;
        } catch (WebClientResponseException e) {
            return new VerifyStudentCallResponseDTO(false, 400, e.getMessage());
        }
    }

    @Override
    public VerifyStudentByEmailResponseDTO verifyStudentByEmail(
            VerifyStudentByEmailRequestDTO verifyStudentByEmailRequestDTO, User user) {

        WebClient client = WebClient.create("https://univcert.com/api/v1/");

        VerifyStudentByEmailResponseDTO response = client.post()
                .uri("/certifycode")
                .body(BodyInserters.fromValue(verifyStudentByEmailRequestDTO))
                .retrieve()
                .bodyToMono(VerifyStudentByEmailResponseDTO.class)
                .block();

        log.info("aaaa ={} {} {}", response.getCode(), response.isSuccess(), response.getMessage());

        if (response.isSuccess()) {
            updateAuthorize(user);
        }

        return response;

    }
}
