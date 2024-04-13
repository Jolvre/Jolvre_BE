package com.example.jolvre.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyStudentCallRequest {
    private String key;
    private String email;
    private String univName;
    private String univ_check;
}
