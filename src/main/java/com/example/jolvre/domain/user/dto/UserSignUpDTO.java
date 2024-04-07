package com.example.jolvre.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignUpDTO {
    private String email;
    private String password;
    private String nickname;
    private int age;
    private String city;
    private String school;

}
