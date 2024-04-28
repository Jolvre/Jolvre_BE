package com.example.jolvre.user.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateEmailResponse;
import com.example.jolvre.user.dto.DuplicationDTO.DuplicateNicknameResponse;
import com.example.jolvre.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "닉네임 중복 체크")
    @GetMapping("/user/check/nickname/{nickname}")
    public ResponseEntity<DuplicateNicknameResponse> checkDuplicateNickname(@PathVariable String nickname) {
        DuplicateNicknameResponse response = userService.checkDuplicateNickname(nickname);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이메일 중복 체크")
    @GetMapping("/user/check/email/{email}")
    public ResponseEntity<DuplicateEmailResponse> checkDuplicateEmail(@PathVariable String email) {
        DuplicateEmailResponse response = userService.checkDuplicateEmail(email);

        return ResponseEntity.ok(response);
    }

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
