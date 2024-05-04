package com.example.jolvre.exhibition.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class DiaryDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DiaryUploadRequest {
        private String title;
        private String content;
        private MultipartFile image;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DiaryGetResponse {
        private Long id;
        private String title;
        private String content;
        private String imageUrl;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DiaryGetResponses {
        private List<DiaryGetResponse> diaryGetResponses;

    }

}
