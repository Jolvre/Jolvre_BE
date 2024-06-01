package com.example.jolvre.auth.api;

import com.example.jolvre.auth.dto.LoginDTO.LoginRequest;
import com.example.jolvre.auth.dto.SignUpDTO.TokenResponse;
import com.example.jolvre.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "회원가입 및 로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok().body(new TokenResponse());
    }

//    @Operation(summary = "학생 인증 메일 요청")
//    @GetMapping("/student/verification")
//    public ResponseEntity<?> verifyEmailSend(@RequestParam StudentVerificationRequest request) {
//        authService.sendStudentVerificationEmail(request);
//
//        return ResponseEntity.ok().build();
//    }

//    @Operation(summary = "학생 메일 인증")
//    @PostMapping("/student/verification")
//    public ResponseEntity<VerifyStudentByEmailResponse> verifyStudentByEmail(
//            @AuthenticationPrincipal PrincipalDetails principalDetails,
//            @RequestBody VerifyStudentByEmailRequest request) {
//        VerifyStudentByEmailResponse response = authService.checkVerificationStudentEmail(
//                request, principalDetails.getUser());
//
//        return ResponseEntity.ok(response);
//    {
}
