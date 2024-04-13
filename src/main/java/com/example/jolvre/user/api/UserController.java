package com.example.jolvre.user.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.dto.UserSignUpDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentByEmailRequestDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentByEmailResponseDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentCallRequestDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentCallResponseDTO;
import com.example.jolvre.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    @Value("verify.student.apiKey")
    private String verifyApiKey;

    private final UserService userService;

    @Operation(summary = "학생 인증")
    @GetMapping("/api/user/verify")
    public User verifyStudent(User user) {
        return null;
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDTO userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @Operation(summary = "추가 회원가입")
    @PostMapping("/api/v1/oauth/signUp")
    public String oauthSignUp(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User update = userService.updateAuthorize(principalDetails.getUser());
        return update.getName();
    }

    @Operation(summary = "학생 인증 메일 요청")
    @PostMapping("/api/v1/student/verify")
    public ResponseEntity<VerifyStudentCallResponseDTO> verifyStudentCalll(
            @RequestBody VerifyStudentCallRequestDTO verifyStudentRequestDTO) {
        VerifyStudentCallResponseDTO response = userService.verifyStudentCall(
                verifyStudentRequestDTO);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "학생 메일 인증")
    @PostMapping("/api/v1/student/verify/email")
    public ResponseEntity<VerifyStudentByEmailResponseDTO> verifyStudentByEmail(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody VerifyStudentByEmailRequestDTO verifyStudentByEmailRequestDTO) {
        VerifyStudentByEmailResponseDTO response = userService.verifyStudentByEmail(
                verifyStudentByEmailRequestDTO, principalDetails.getUser());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/jwt-test")
    public String jwtTest(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("name =  {}", principalDetails.getUser().getName());
        return "jwtTest 요청 성공";
    }

}
