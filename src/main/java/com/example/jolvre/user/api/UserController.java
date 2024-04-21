package com.example.jolvre.user.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @GetMapping("/jwt-test")
    public String jwtTest(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("name =  {}", principalDetails.getUser().getEmail());
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
