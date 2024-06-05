package com.example.jolvre.auth.api;

import com.example.jolvre.auth.dto.LoginDTO.LoginRequest;
import com.example.jolvre.auth.dto.SignUpDTO.RefreshRequest;
import com.example.jolvre.auth.dto.SignUpDTO.TokenResponse;
import com.example.jolvre.auth.email.dto.EmailDTO.EmailSendResponse;
import com.example.jolvre.auth.email.dto.EmailDTO.EmailVerifyRequest;
import com.example.jolvre.auth.email.dto.EmailDTO.FindPwEmailVerifyResponse;
import com.example.jolvre.auth.email.service.MailService;
import com.example.jolvre.auth.email.service.MailVerifyService;
import com.example.jolvre.auth.jwt.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "회원가입 및 로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final JwtService jwtService;
    private final MailService mailService;
    private final MailVerifyService mailVerifyService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok().body(new TokenResponse());
    }

    @Operation(summary = "리프레쉬 토큰을 통한 재발급", description = "토큰을 재발급 합니다")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        TokenResponse response = jwtService.checkRefreshTokenAndReIssueAccessToken(request.getRefreshToken());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "비밀번호 찾기 인증 메일 발송", description = "비밀번호 찾기 인증 메일을 발송합니다")
    @GetMapping("/pw/email/{email}")
    public ResponseEntity<EmailSendResponse> sendPwFindAuthEmail(@PathVariable String email) {
        EmailSendResponse response = mailService.sendFindPwEmail(email);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "비밀번호 찾기 인증 메일 검증", description = "비밀번호 찾기 인증 메일을 검증합니다")
    @PostMapping("/pw/email")
    public ResponseEntity<FindPwEmailVerifyResponse> verifyPwFindAuthEmail(@RequestBody EmailVerifyRequest request) {
        FindPwEmailVerifyResponse response = mailVerifyService.CheckFindPwAuthNum(request.getEmail(),
                request.getAuthNum());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "비밀번호 수정", description = "비밀번호 찾기 인증 메일을 검증합니다")
    @PutMapping("/pw")
    public ResponseEntity<Void> verifyPwFindAuthEmai(@RequestBody EmailVerifyRequest request) {
        FindPwEmailVerifyResponse response = mailVerifyService.CheckFindPwAuthNum(request.getEmail(),
                request.getAuthNum());

        return ResponseEntity.ok().build();
    }
}
