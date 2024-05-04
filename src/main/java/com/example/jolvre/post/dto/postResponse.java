package com.example.jolvre.post.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class postResponse {
    private String title;
    private String content;
}