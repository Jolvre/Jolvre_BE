package com.example.jolvre.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DuplicationDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DuplicateEmailRequest {
        private String email;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DuplicateNicknameRequest {
        private String nickname;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DuplicateEmailResponse {
        private boolean duplicate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DuplicateNicknameResponse {
        private boolean duplicate;
    }
}
