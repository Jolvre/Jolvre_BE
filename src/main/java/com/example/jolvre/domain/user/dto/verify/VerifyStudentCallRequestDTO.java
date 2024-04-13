package com.example.jolvre.domain.user.dto.verify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyStudentCallRequestDTO {
    private String key;
    private String email;
    private String univName;
    private String univ_check;
}
