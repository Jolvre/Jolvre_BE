package com.example.jolvre.auth.api;

import static com.example.jolvre.auth.email.dto.EmailDTO.EmailCheckRequest;
import static com.example.jolvre.auth.email.dto.EmailDTO.EmailSendRequest;

import com.example.jolvre.auth.email.service.MailSenderService;
import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.auth.login.dto.UserSignUpDTO;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyEmailSendRequest;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyEmailSendResponse;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyStudentByEmailRequest;
import com.example.jolvre.auth.login.dto.VerifyStudentDTO.VerifyStudentByEmailResponse;
import com.example.jolvre.auth.service.AuthService;
import com.example.jolvre.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final MailSenderService mailService;

    @Operation(summary = "회원 가입")
    @PostMapping("/api/v1/auth/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignUpDto) throws Exception {
        log.info("[AUTH] : 기본 회원가입");
        authService.signUp(userSignUpDto);

        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "추가 회원가입")
    @PostMapping("/api/v1/auth/oauth/signUp")
    public ResponseEntity<String> oauthSignUp(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[AUTH] : OAUTH 회원가입");

        User update = authService.updateAuthorize(principalDetails.getUser());

        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "메일 인증 요청")
    @PostMapping("/api/v1/auth/mailSend")
    public ResponseEntity<String> mailSend(@RequestBody @Valid EmailSendRequest emailDto) {
        return ResponseEntity.ok(mailService.joinEmail(emailDto.getEmail()));
    }

    @Operation(summary = "메일 인증")
    @PostMapping("/api/v1/auth/mailAuthCheck")
    public String AuthCheck(@RequestBody @Valid EmailCheckRequest emailCheckDto) {
        boolean Checked = mailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
        if (Checked) {
            return "ok";
        } else {
            throw new NullPointerException("뭔가 잘못!");
        }
    }

    @Operation(summary = "학생 인증 메일 요청")
    @PostMapping("/api/v1/user/student/verify")
    public ResponseEntity<VerifyEmailSendResponse> verifyEmailSend(
            @RequestBody VerifyEmailSendRequest request) {
        VerifyEmailSendResponse response = authService.verifyStudentCall(
                request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "학생 메일 인증")
    @PostMapping("/api/v1/user/student/verify/email")
    public ResponseEntity<VerifyStudentByEmailResponse> verifyStudentByEmail(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody VerifyStudentByEmailRequest request) {
        VerifyStudentByEmailResponse response = authService.verifyStudentByEmail(
                request, principalDetails.getUser());

        return ResponseEntity.ok(response);
    }
}
