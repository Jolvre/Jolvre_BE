package com.example.jolvre.post.dto;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class postRequest {
    private String title;
    private String content;
}