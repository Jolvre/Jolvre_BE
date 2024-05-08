package com.example.jolvre.auth.api;

import com.example.jolvre.auth.email.service.MailSenderService;
import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.auth.login.dto.LoginDTO.LoginRequest;
import com.example.jolvre.auth.login.dto.SignUpDTO.TokenResponse;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyEmailSendRequest;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyEmailSendResponse;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyStudentByEmailRequest;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyStudentByEmailResponse;
import com.example.jolvre.auth.service.AuthService;
import com.example.jolvre.common.error.user.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "회원가입 및 로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final MailSenderService mailService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok().body(new TokenResponse());
    }

    @Operation(summary = "학생 인증 메일 요청")
    @GetMapping("/student/verification")
    public ResponseEntity<VerifyEmailSendResponse> verifyEmailSend(
            @ParameterObject @RequestParam VerifyEmailSendRequest request) {
        VerifyEmailSendResponse response = authService.sendStudentVerificationEmail(
                request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "학생 메일 인증")
    @PostMapping("/student/verification")
    public ResponseEntity<VerifyStudentByEmailResponse> verifyStudentByEmail(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @ParameterObject @RequestBody VerifyStudentByEmailRequest request) {
        VerifyStudentByEmailResponse response = authService.checkVerificationStudentEmail(
                request, principalDetails.getUser());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test") // 인증 실패 추가폼 더미
    public String aa() {
        throw new UserNotFoundException();
    }
}
