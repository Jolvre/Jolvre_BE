package com.example.jolvre.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyStudentCallResponse {
    private boolean success;
    private int code;
    private String message;

    public VerifyStudentCallResponse(boolean success) {
        this.success = success;
    }
}
