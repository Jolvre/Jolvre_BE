package com.example.jolvre.auth.api;

import static com.example.jolvre.auth.email.dto.EmailDTO.EmailCheckRequest;
import static com.example.jolvre.auth.email.dto.EmailDTO.EmailSendRequest;

import com.example.jolvre.auth.email.service.MailSenderService;
import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.auth.login.dto.UserSignUpDTO;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
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
    private final UserService userService;
    private final MailSenderService mailService;

    @Operation(summary = "회원 가입")
    @PostMapping("/api/v1/auth/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignUpDto) throws Exception {
        log.info("[AUTH] : 기본 회원가입");
        userService.signUp(userSignUpDto);

        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "추가 회원가입")
    @PostMapping("/api/v1/auth/oauth/signUp")
    public ResponseEntity<String> oauthSignUp(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[AUTH] : OAUTH 회원가입");

        User update = userService.updateAuthorize(principalDetails.getUser());

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
}
