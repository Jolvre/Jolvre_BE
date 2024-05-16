package com.example.jolvre.user.dto;

import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserInfoResponse {
        private Long id;
        private String email;
        private String name;
        private String nickname;
        private int age;
        private String school;
        private String city;
        private Role role;
        private String imageUrl;

        public static UserInfoResponse toDTO(User user) {
            return new UserInfoResponse(user.getId(), user.getEmail(), user.getName(), user.getNickname(),
                    user.getAge(),
                    user.getSchool(),
                    user.getCity(), user.getRole(), user.getImageUrl());
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserUpdateRequest {
        private String name;
        private String nickname;
        private int age;
        private String city;
        private MultipartFile image;
    }
}
