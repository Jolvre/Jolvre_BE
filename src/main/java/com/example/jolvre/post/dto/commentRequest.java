package com.example.jolvre.post.dto;


import com.example.jolvre.post.entity.Comment;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class commentRequest {
    private String content;
}
