package com.example.jolvre.exhibition.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class ExhibitDTO {

    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ExhibitUploadRequest {
        private String title;
        private String authorWord;
        private String introduction;
        private String size;
        private String productionMethod;
        private int price;
        private boolean forSale;
        private List<MultipartFile> images;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ExhibitResponse {
        private String title;
        private String authorWord;
        private String introduction;
        private String size;
        private String productionMethod;
        private int price;
        private boolean forSale;
        private List<String> imagesUrl;
    }
}
