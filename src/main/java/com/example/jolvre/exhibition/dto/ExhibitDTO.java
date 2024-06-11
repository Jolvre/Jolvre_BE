package com.example.jolvre.exhibition.dto;

import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponses;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.entity.ExhibitComment;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.multipart.MultipartFile;

public class ExhibitDTO {
    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ExhibitUpdateRequest {
        private String title;
        private String authorWord;
        private String introduction;
        private String size;
        private String productionMethod;

        private int price;
        private boolean forSale;
        private boolean checkVirtualSpace;
        private String workType;

        private String backgroundImage3d;
        private String backgroundImage2d;

        private MultipartFile thumbnail;
        private List<MultipartFile> images;

    }


    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitUploadRequest {
        private String title;
        private String authorWord;
        private String introduction;
        private String size;
        private String productionMethod;
        private int price;
        private boolean forSale;
        private boolean checkVirtualSpace;
        private String workType;
        private String backgroundImage3d;
        private String backgroundImage2d;
        private MultipartFile thumbnail;
        private List<MultipartFile> images;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    public static class ExhibitInfoResponse {
        private Long id;
        private String title;
        private String authorWord;
        private String introduction;
        private String size;
        private String productionMethod;
        private int price;
        private boolean forSale;
        private String thumbnail;
        private List<String> imagesUrl;
        private String image3d;
        private boolean distribute;
        private boolean checkVirtualSpace;
        private String workType;
        private String background2dImage;
        private String background3dImage;
        private UserInfoResponse userInfoResponse;
        private DiaryInfoResponses diaryInfoResponses;

        @QueryProjection
        public ExhibitInfoResponse(Long id, String title, String authorWord, String introduction, String size,
                                   String productionMethod, int price, boolean forSale, String thumbnail,
                                   List<String> imagesUrl, String image3d, boolean distribute,
                                   boolean checkVirtualSpace,
                                   String workType, String background2dImage, String background3dImage,
                                   UserInfoResponse userInfoResponse, DiaryInfoResponses diaryInfoResponses) {
            this.id = id;
            this.title = title;
            this.authorWord = authorWord;
            this.introduction = introduction;
            this.size = size;
            this.productionMethod = productionMethod;
            this.price = price;
            this.forSale = forSale;
            this.thumbnail = thumbnail;
            this.imagesUrl = imagesUrl;
            this.image3d = image3d;
            this.distribute = distribute;
            this.checkVirtualSpace = checkVirtualSpace;
            this.workType = workType;
            this.background2dImage = background2dImage;
            this.background3dImage = background3dImage;
            this.userInfoResponse = userInfoResponse;
            this.diaryInfoResponses = diaryInfoResponses;
        }

        public static ExhibitInfoResponse toDTO(Exhibit exhibit) {
            return new ExhibitInfoResponse(exhibit.getId(), exhibit.getTitle(),
                    exhibit.getAuthorWord(), exhibit.getIntroduction(),
                    exhibit.getSize(), exhibit.getProductionMethod(), exhibit.getPrice(),
                    exhibit.isForSale(), exhibit.getThumbnail(), exhibit.getImageUrls(), exhibit.getImage3d(),
                    exhibit.isDistribute(), exhibit.isCheckVirtualSpace(), exhibit.getWorkType(),
                    exhibit.getBackground2dImage(), exhibit.getBackground3dImage(),
                    UserInfoResponse.toDTO(exhibit.getUser()), DiaryInfoResponses.toDTO(exhibit.getDiaries())
            );
        }

    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitInfoResponses {
        private List<ExhibitInfoResponse> exhibitResponses;

        public static ExhibitInfoResponses toDTO(List<Exhibit> exhibits) {
            List<ExhibitInfoResponse> responses = new ArrayList<>();

            exhibits.forEach(exhibit -> responses.add(ExhibitInfoResponse.toDTO(exhibit)));

            return new ExhibitInfoResponses(responses);
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitUploadResponse {
        private Long exhibitId;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitInvitationResponse {
        private String thumbnail;
        private String title;
        private String introduction;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @Jacksonized
    public static class ExhibitCommentUploadRequest {
        private String content;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @Jacksonized
    public static class ExhibitCommentUpdateRequest {
        private String content;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitCommentInfoResponse {
        private Long commentId;
        private String content;

        private UserInfoResponse userInfo;

        public static ExhibitCommentInfoResponse toDTO(ExhibitComment comment) {
            return new ExhibitCommentInfoResponse(comment.getId(), comment.getContent(),
                    UserInfoResponse.toDTO(comment.getUser()));
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ExhibitCommentInfoResponses {
        private List<ExhibitCommentInfoResponse> responses;

        public static ExhibitCommentInfoResponses toDTO(List<ExhibitComment> comments) {
            List<ExhibitCommentInfoResponse> response = new ArrayList<>();

            comments.forEach((comment -> response.add(ExhibitCommentInfoResponse.toDTO(comment))));

            return new ExhibitCommentInfoResponses(response);
        }
    }
}
