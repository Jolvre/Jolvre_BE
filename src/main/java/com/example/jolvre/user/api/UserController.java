package com.example.jolvre.user.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.user.dto.VerifyStudentByEmailRequest;
import com.example.jolvre.user.dto.VerifyStudentByEmailResponse;
import com.example.jolvre.user.dto.VerifyStudentCallRequest;
import com.example.jolvre.user.dto.VerifyStudentCallResponse;
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
    @PostMapping("/api/v1/student/verify")
    public ResponseEntity<VerifyStudentCallResponse> verifyStudentCalll(
            @RequestBody VerifyStudentCallRequest verifyStudentRequestDTO) {
        VerifyStudentCallResponse response = userService.verifyStudentCall(
                verifyStudentRequestDTO);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "학생 메일 인증")
    @PostMapping("/api/v1/student/verify/email")
    public ResponseEntity<VerifyStudentByEmailResponse> verifyStudentByEmail(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody VerifyStudentByEmailRequest verifyStudentByEmailRequest) {
        VerifyStudentByEmailResponse response = userService.verifyStudentByEmail(
                verifyStudentByEmailRequest, principalDetails.getUser());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/jwt-test")
    public String jwtTest(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("name =  {}", principalDetails.getUser().getName());
        return "jwtTest 요청 성공";
    }

}
