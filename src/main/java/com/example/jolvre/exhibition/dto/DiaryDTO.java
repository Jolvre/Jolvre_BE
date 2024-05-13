package com.example.jolvre.exhibition.dto;

import com.example.jolvre.exhibition.entity.Diary;
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

        public static DiaryGetResponse from(Diary diary) {
            return new DiaryGetResponse(diary.getId(), diary.getTitle(), diary.getContent(), diary.getImageUrl());
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DiaryGetResponses {
        private List<DiaryGetResponse> diaryGetResponses;

    }

}
