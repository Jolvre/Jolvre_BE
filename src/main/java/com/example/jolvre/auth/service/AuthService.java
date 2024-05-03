package com.example.jolvre.auth.service;

import com.example.jolvre.auth.jwt.service.JwtService;
import com.example.jolvre.auth.login.dto.SignUpDTO.BasicSignUpRequest;
import com.example.jolvre.auth.login.dto.SignUpDTO.OauthSignUpRequest;
import com.example.jolvre.auth.login.dto.SignUpDTO.TokenResponse;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyEmailSendRequest;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyEmailSendResponse;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyStudentByEmailRequest;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyStudentByEmailResponse;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateEmailResponse;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateNicknameResponse;
import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final String VERIFY_STUDENT_API_URI = "https://univcert.com/api/v1/";
    private final String VERIFY_CODE_CALL = "/certify";
    private final String VERIFY_EMAIL = "/certifycode";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public TokenResponse signUpBasic(BasicSignUpRequest request) {

        String accessToken = jwtService.createAccessToken(request.getEmail());
        String refreshToken = jwtService.createRefreshToken();

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .nickname(request.getNickname())
                .age(request.getAge())
                .city(request.getCity())
                .school(request.getSchool())
                .role(Role.USER)
                .refreshToken(accessToken)
                .build();

        user.passwordEncode(passwordEncoder);

        log.info("[AUTH] : 회원가입 성공");

        userRepository.save(user);

        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }

    @Transactional
    public void signUpOauth(OauthSignUpRequest request, User user) {

        User oauthUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        log.info("----------- {}", request.getName());

        oauthUser.setName(request.getName());
        oauthUser.setAge(request.getAge());
        oauthUser.setCity(request.getCity());
        oauthUser.setSchool(request.getSchool());
        oauthUser.setRole(Role.USER);

        userRepository.save(oauthUser);

        log.info("[AUTH] : 추가 회원가입 성공");
    }

//    @Transactional
//    public void signUpOauth(OauthSignUpRequest request, User user) {
//
//        User oauthUser = userRepository.findById(user.getId())
//                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));
//
//        log.info("----------- {}", request.getName());
//
//        oauthUser.setName(request.getName());
//        oauthUser.setAge(request.getAge());
//        oauthUser.setCity(request.getCity());
//        oauthUser.setSchool(request.getSchool());
//        oauthUser.setRole(Role.USER);
//
//        userRepository.save(oauthUser);
//
//        log.info("[AUTH] : 추가 회원가입 성공");
//    }

    @Transactional
    public User updateAuthorize(User user) {

        log.info("[USER] : 유저 권한 변경 {} -> {}", user.getRole().getKey(), Role.STUDENT.getKey());

        User updaeteUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        updaeteUser.authorizeStudent();

        return updaeteUser;
    }

    @Transactional
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

    public DuplicateEmailResponse checkDuplicateEmail(String email) {
        return new DuplicateEmailResponse(userRepository.existsByEmail(email));
    }

    public DuplicateNicknameResponse checkDuplicateNickname(String nickname) {
        return new DuplicateNicknameResponse(userRepository.existsByNickname(nickname));
    }
}
