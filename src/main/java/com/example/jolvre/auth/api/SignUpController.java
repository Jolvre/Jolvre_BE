package com.example.jolvre.auth.api;

import com.example.jolvre.auth.email.dto.EmailDTO;
import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.auth.login.dto.SignUpDTO.BasicSignUpRequest;
import com.example.jolvre.auth.login.dto.SignUpDTO.OauthSignUpRequest;
import com.example.jolvre.auth.login.dto.SignUpDTO.TokenResponse;
import com.example.jolvre.auth.service.AuthService;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateEmailResponse;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateNicknameResponse;
import com.example.jolvre.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/signUp")
public class SignUpController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "회원 가입")
    @PostMapping()
    public ResponseEntity<TokenResponse> signUpBasic(@RequestBody BasicSignUpRequest request) throws Exception {
        log.info("[AUTH] : 기본 회원가입");
        TokenResponse response = authService.signUpBasic(request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "추가 회원가입")
    @PostMapping("/oauth")
    //수정 필요
    public ResponseEntity<String> signUpOauth(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @RequestBody OauthSignUpRequest request) {
        log.info("[AUTH] : OAUTH 회원가입");

        authService.signUpOauth(request, principalDetails.getUser());

        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "닉네임 중복 체크")
    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<DuplicateNicknameResponse> checkDuplicateNickname(@PathVariable String nickname) {
        DuplicateNicknameResponse response = userService.checkDuplicateNickname(nickname);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이메일 중복 체크")
    @GetMapping("/check/email/{email}")
    public ResponseEntity<DuplicateEmailResponse> checkDuplicateEmail(@PathVariable String email) {
        DuplicateEmailResponse response = userService.checkDuplicateEmail(email);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "메일 인증 요청")
    @GetMapping("/mail")
    public ResponseEntity<String> mailSend(@RequestBody @Valid EmailDTO.EmailSendRequest emailDto) {
//        return ResponseEntity.ok(mailService.joinEmail(emailDto.getEmail()));

        return null;
    }

    @Operation(summary = "메일 인증")
    @GetMapping("/mail/verify")
    public String AuthCheck(@RequestBody @Valid EmailDTO.EmailCheckRequest emailCheckDto) {
//        boolean Checked = mailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
//        if (Checked) {
//            return "ok";
//        } else {
//            throw new NullPointerException("뭔가 잘못!");
//        }

        return null;
    }


}
