package com.example.jolvre.auth.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {
    private String email;
    private String password;
    private String nickname;
    private int age;
    private String city;
    private String school;

}
