package com.example.jolvre.user.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import com.example.jolvre.user.dto.UserDTO.UserUpdateRequest;
import com.example.jolvre.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[USER] : {} 님 정보 조회", principalDetails.getId());

        return ResponseEntity.ok(userService.getUserInfo(principalDetails.getId()));
    }

    @Operation(summary = "유저 정보 수정")
    @PatchMapping(path = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@ModelAttribute UserUpdateRequest request
            , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[USER] : {} 님 정보 수정", principalDetails.getId());

        userService.updateUser(principalDetails.getId(), request);
        return ResponseEntity.ok().build();
    }

}
