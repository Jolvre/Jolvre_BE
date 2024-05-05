package com.example.jolvre.post.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class commentResponse {
    private Long commentId;
    private String content;
    private String userName;
}
