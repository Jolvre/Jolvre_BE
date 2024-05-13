package com.example.jolvre.group.dto;

import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponses;
import com.example.jolvre.group.entity.GroupExhibit;
import com.example.jolvre.user.dto.UserDTO.UserInfoResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GroupExhibitDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupExhibitCreateRequest {
        private String name;
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
    public static class GroupExhibitResponse {
        private Long id;
        private String name;
        private ExhibitResponses exhibits;

        public static GroupExhibitResponse toDTO(GroupExhibit groupExhibit) {
            return new GroupExhibitResponse(groupExhibit.getId(), groupExhibit.getName(),
                    ExhibitResponses.toDTO(groupExhibit.getExhibits()));
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GroupExhibitResponses {
        private List<GroupExhibitResponse> groupExhibitResponses;

        public static GroupExhibitResponses toDTO(List<GroupExhibit> groupExhibits) {
            List<GroupExhibitResponse> responses = new ArrayList<>();
            groupExhibits.forEach(group -> responses.add(GroupExhibitResponse.toDTO(group)));

            return new GroupExhibitResponses(responses);
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
}
