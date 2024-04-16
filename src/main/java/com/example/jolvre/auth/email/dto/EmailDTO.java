package com.example.jolvre.auth.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EmailDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailCheckRequest {
        @Email
        @NotEmpty(message = "이메일을 입력해 주세요")
        private String email;

        @NotEmpty(message = "인증 번호를 입력해 주세요")
        private String authNum;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailSendRequest {
        @Email
        @NotEmpty(message = "이메일을 입력해 주세요")
        private String email;

    }
}
