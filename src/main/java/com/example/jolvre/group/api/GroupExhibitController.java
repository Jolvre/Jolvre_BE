package com.example.jolvre.group.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitCreateRequest;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitInfoResponse;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitInfoResponses;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitUserResponses;
import com.example.jolvre.group.service.GroupExhibitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//todo : update 기능

@Tag(name = "Group Exhibit", description = "단체전시 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupExhibitController {
    private final GroupExhibitService groupExhibitService;

    @Operation(summary = "단체 전시 생성")
    @PostMapping
    public ResponseEntity<?> createGroup(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @RequestBody GroupExhibitCreateRequest request) {
        groupExhibitService.createGroupExhibit(principalDetails.getId(), request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "단체 전시 조회 (단체 전시 탭에서)")
    @GetMapping("/groups")
    public ResponseEntity<GroupExhibitInfoResponses> getAllGroupExhibit() {
        GroupExhibitInfoResponses responses = groupExhibitService.getAllGroupExhibitInfo();

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "단체 전시 상세 조회")
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<GroupExhibitInfoResponse> getGroupExhibit(@PathVariable Long groupId) {
        GroupExhibitInfoResponse response = groupExhibitService.getGroupExhibitInfo(groupId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "유저의 단체 전시 조회(유저 탭에서)")
    @GetMapping("/user")
    public ResponseEntity<GroupExhibitInfoResponses> getAllUserGroupExhibit(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        GroupExhibitInfoResponses responses = groupExhibitService.getAllUserGroupExhibitInfo(principalDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "단체 전시 전시 추가")
    @PostMapping("/{groupId}/exhibit/{exhibitId}")
    public ResponseEntity<?> addExhibit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PathVariable Long groupId, @PathVariable Long exhibitId
    ) {
        groupExhibitService.addExhibit(principalDetails.getId(), groupId, exhibitId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "단체 전시 회원 조회")
    @GetMapping("/groups/{groupId}/users")
    public ResponseEntity<?> getGroupExhibitUsers(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                  @PathVariable Long groupId) {
        GroupExhibitUserResponses responses =
                groupExhibitService.getGroupExhibitUsers(principalDetails.getId(), groupId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "매니저 추가")
    @PostMapping("/{groupId}/manager/{toUserId}")
    public ResponseEntity<?> addManager(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PathVariable Long toUserId,
                                        @PathVariable Long groupId
    ) {
        groupExhibitService.addManager(principalDetails.getId(), toUserId, groupId);

        return ResponseEntity.ok().build();
    }
}
