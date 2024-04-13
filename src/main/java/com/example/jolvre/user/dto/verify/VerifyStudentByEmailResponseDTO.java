package com.example.jolvre.user.dto.verify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyStudentByEmailResponseDTO {
    private boolean success;
    private int code;
    private String message;
}
