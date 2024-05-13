package com.example.jolvre.auth.service;

import com.example.jolvre.auth.dto.SignUpDTO.BasicSignUpRequest;
import com.example.jolvre.auth.dto.SignUpDTO.OauthSignUpRequest;
import com.example.jolvre.auth.dto.SignUpDTO.TokenResponse;
import com.example.jolvre.auth.jwt.service.JwtService;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateEmailResponse;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateNicknameResponse;
import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpService {
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

    public DuplicateEmailResponse checkDuplicateEmail(String email) {
        return new DuplicateEmailResponse(userRepository.existsByEmail(email));
    }

    public DuplicateNicknameResponse checkDuplicateNickname(String nickname) {
        return new DuplicateNicknameResponse(userRepository.existsByNickname(nickname));
    }
}

