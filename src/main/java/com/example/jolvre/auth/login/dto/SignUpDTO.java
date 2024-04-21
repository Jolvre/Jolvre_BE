package com.example.jolvre.auth.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SignUpDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BasicSignUpRequest {
        private String email;
        private String name;
        private String password;
        private String nickname;
        private int age;
        private String city;
        private String school;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OauthSignUpRequest {
        private String name;
        private int age;
        private String city;
        private String school;

    }
}
