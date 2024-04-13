package com.example.jolvre.domain.user.dto.verify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyStudentByEmailRequestDTO {
    private String key;
    private String email;
    private String univName;
    private int code;
}
