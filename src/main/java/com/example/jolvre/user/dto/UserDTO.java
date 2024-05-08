package com.example.jolvre.user.dto;

import com.example.jolvre.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserInfoResponse {
        private String name;
        private String nickname;
        private int age;
        private String school;
        private String city;
        private Role role;
        private String imageUrl;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserUpdateRequest {
        private String name;
        private String nickname;
        private int age;
        private String city;
        private String imageUrl;
    }
}
