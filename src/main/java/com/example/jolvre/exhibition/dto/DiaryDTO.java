package com.example.jolvre.exhibition.dto;

import com.example.jolvre.exhibition.entity.Diary;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class DiaryDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @Setter
    public static class DiaryUploadRequest {
        private String title;
        private String content;
        private MultipartFile image;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class DiaryUpdateRequest {
        private String title;
        private String content;
        private MultipartFile image;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DiaryInfoResponse {
        private Long id;
        private String title;
        private String content;
        private String imageUrl;
        private LocalDateTime startDate;

        public static DiaryInfoResponse toDTO(Diary diary) {
            return new DiaryInfoResponse(diary.getId(), diary.getTitle(), diary.getContent(), diary.getImageUrl(),
                    diary.getCreatedDate());
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DiaryInfoResponses {
        private List<DiaryInfoResponse> diaryGetResponses;

        public static DiaryInfoResponses toDTO(List<Diary> diaries) {
            List<DiaryInfoResponse> responses = new ArrayList<>();
            diaries.forEach(diary -> responses.add(DiaryInfoResponse.toDTO(diary)));
            return new DiaryInfoResponses(responses);

        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ImageUploadRequest {
        private MultipartFile image;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class DiaryImagesResponse {
        private List<String> images;
    }
}
