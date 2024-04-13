package com.example.jolvre.auth.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.auth.login.dto.UserSignUpDTO;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

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

}
