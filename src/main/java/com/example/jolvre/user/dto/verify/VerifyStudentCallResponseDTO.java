package com.example.jolvre.user.dto.verify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyStudentCallResponseDTO {
    private boolean success;
    private int code;
    private String message;

    public VerifyStudentCallResponseDTO(boolean success) {
        this.success = success;
    }
}
