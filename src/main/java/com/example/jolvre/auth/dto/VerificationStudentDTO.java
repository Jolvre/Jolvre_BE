package com.example.jolvre.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VerificationStudentDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentVerificationRequest {
        private String email;
        private String univName;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentVerificationApiFormat {
        private String key;
        private String email;
        private String univName;
        private boolean univ_check;

        @Builder
        public StudentVerificationApiFormat(String key, StudentVerificationRequest request) {
            this.email = request.getEmail();
            this.univName = request.getUnivName();
            this.key = key;
            univ_check = true;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentVerificationResponse {
        private int status;
        private String message;
        private boolean success;
    }

}
