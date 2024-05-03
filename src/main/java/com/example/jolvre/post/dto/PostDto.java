package com.example.jolvre.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PostDto {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostUploadRequest
    {
        private String title;
        private String content;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostResponse
    {
        private String title;
        private String content;
    }
}
