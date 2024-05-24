package com.example.jolvre.group.dto;

import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInfoResponses;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class GroupExhibitDTO {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class GroupExhibitCreateRequest {
        private String name;
        private String period;
        private String selectedItem;
        private String introduction;
        private MultipartFile thumbnail;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupExhibitAddExhibitRequest {
        private String nickname;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupExhibitInfoResponse {
        private Long id;
        private String name;
        private String period;
        private String selectedItem;
        private String introduction;
        private String thumbnail;

        private ExhibitInfoResponses exhibits;

        public static GroupExhibitInfoResponse toDTO(GroupExhibit groupExhibit) {
            return new GroupExhibitInfoResponse(groupExhibit.getId(), groupExhibit.getName(), groupExhibit.getPeriod(),
                    groupExhibit.getSelectedItem(), groupExhibit.getIntroduction(), groupExhibit.getThumbnail(),
                    ExhibitInfoResponses.toDTO(groupExhibit.getRegisteredExhibitInfo()));
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupExhibitInfoResponses {
        private List<GroupExhibitInfoResponse> groupExhibitResponses;

        public static GroupExhibitInfoResponses toDTO(List<GroupExhibit> groupExhibits) {
            List<GroupExhibitInfoResponse> responses = new ArrayList<>();
            groupExhibits.forEach(group -> responses.add(GroupExhibitInfoResponse.toDTO(group)));

            return new GroupExhibitInfoResponses(responses);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupExhibitUserResponse {
        private String role;
        private UserInfoResponse userInfoResponse;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupExhibitUserResponses {
        private List<GroupExhibitUserResponse> groupExhibitUserResponses;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupUpdateRequest {
        private String name;
        private String introduction;

        private MultipartFile thumbnail;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupInvitationResponse {
        private String name;
        private String introduction;
        private String thumbnail;
    }
}
