package com.example.jolvre.common.firebase.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationRequestDto {

    private String title;
    private String msg;
    private String token;

    @Builder
    public NotificationRequestDto(String title, String msg, String token) {
        this.title = title;
        this.msg = msg;
        this.token = token;
    }
}
