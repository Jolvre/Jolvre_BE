package com.example.jolvre.user.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.user.dto.VerifyStudentDTO.VerifyEmailSendRequest;
import com.example.jolvre.user.dto.VerifyStudentDTO.VerifyEmailSendResponse;
import com.example.jolvre.user.dto.VerifyStudentDTO.VerifyStudentByEmailRequest;
import com.example.jolvre.user.dto.VerifyStudentDTO.VerifyStudentByEmailResponse;
import com.example.jolvre.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserService userService;

    @Operation(summary = "학생 인증 메일 요청")
    @PostMapping("/api/v1/user/student/verify")
    public ResponseEntity<VerifyEmailSendResponse> verifyEmailSend(
            @RequestBody VerifyEmailSendRequest request) {
        VerifyEmailSendResponse response = userService.verifyStudentCall(
                request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "학생 메일 인증")
    @PostMapping("/api/v1/user/student/verify/email")
    public ResponseEntity<VerifyStudentByEmailResponse> verifyStudentByEmail(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody VerifyStudentByEmailRequest request) {
        VerifyStudentByEmailResponse response = userService.verifyStudentByEmail(
                request, principalDetails.getUser());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/jwt-test")
    public String jwtTest(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("name =  {}", principalDetails.getUser().getName());
        return "jwtTest 요청 성공";
    }

    @GetMapping("/test")
    public String test() {
        return "!!!!RETRTSDSTADT";
    }

    @GetMapping("/test2")
    public String test2() {
        return "!!!!RETRTSDSTADT";
    }

}
