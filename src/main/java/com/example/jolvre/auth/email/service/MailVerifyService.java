package com.example.jolvre.auth.email.service;

import com.example.jolvre.auth.dto.SignUpDTO.TokenResponse;
import com.example.jolvre.auth.email.dto.EmailDTO.FindPwEmailVerifyResponse;
import com.example.jolvre.auth.email.dto.EmailDTO.SignUpEmailVerifyResponse;
import com.example.jolvre.auth.jwt.service.JwtService;
import com.example.jolvre.common.error.user.UserAlreadyExistException;
import com.example.jolvre.common.error.user.UserNotFoundException;
import com.example.jolvre.common.util.RedisUtil;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailVerifyService {
    private final RedisUtil redisUtil;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SignUpEmailVerifyResponse CheckSignUpAuthNum(String email, String authNum) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistException();
        }

        if (redisUtil.getData(email) == null) {
            return SignUpEmailVerifyResponse.builder()
                    .verifyResult(false)
                    .email(email)
                    .build();

        } else if (redisUtil.getData(email).equals(authNum)) {
            redisUtil.deleteData(email);
            return SignUpEmailVerifyResponse.builder()
                    .verifyResult(true)
                    .email(email)
                    .build();
        }

        return SignUpEmailVerifyResponse.builder()
                .verifyResult(false)
                .email(email)
                .build();
    }

    public FindPwEmailVerifyResponse CheckFindPwAuthNum(String email, String authNum) {
        User targetUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (redisUtil.getData(email) == null) {
            return FindPwEmailVerifyResponse.builder()
                    .verifyResult(false)
                    .email(email)
                    .build();
        } else if (redisUtil.getData(email).equals(authNum)) {
            redisUtil.deleteData(email);

            TokenResponse token = TokenResponse.builder()
                    .accessToken(jwtService.createAccessToken(targetUser.getEmail()))
                    .refreshToken(jwtService.reIssueRefreshToken(targetUser))
                    .build();

            return FindPwEmailVerifyResponse.builder()
                    .verifyResult(true)
                    .email(email)
                    .tokenResponse(token)
                    .build();
        }

        return FindPwEmailVerifyResponse.builder()
                .verifyResult(false)
                .email(email)
                .build();
    }
}
