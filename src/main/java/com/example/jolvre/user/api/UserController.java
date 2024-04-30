package com.example.jolvre.user.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import com.example.jolvre.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회")
    @GetMapping
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return ResponseEntity.ok(userService.getUser(principalDetails.getId()));
    }

    @Operation(summary = "유저 정보 수정")
    @PatchMapping
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return ResponseEntity.ok().build();
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
