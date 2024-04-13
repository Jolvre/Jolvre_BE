package com.example.jolvre.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyStudentByEmailRequest {
    private String key;
    private String email;
    private String univName;
    private int code;
}
