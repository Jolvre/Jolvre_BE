package com.example.jolvre.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VerifyStudentDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VerifyStudentByEmailRequest {
        private String key;
        private String email;
        private String univName;
        private int code;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyStudentByEmailResponse {
        private boolean success;
        private int code;
        private String message;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyEmailSendRequest {
        private String key;
        private String email;
        private String univName;
        private String univ_check;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VerifyEmailSendResponse {
        private boolean success;
        private int code;
        private String message;

        public VerifyEmailSendResponse(boolean success) {
            this.success = success;
        }
    }
}
