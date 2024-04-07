package com.example.jolvre.controller;

import com.example.jolvre.domain.user.User;
import com.example.jolvre.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping("/api/user/verify")
    public User verifyStudent(User user) {
        return userService.verifyStudent(user);
    }


}
