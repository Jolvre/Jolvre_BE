package com.example.jolvre.group.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitCreateRequest;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitResponse;
import com.example.jolvre.group.dto.GroupExhibitDTO.GroupExhibitResponses;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                                         GroupExhibitCreateRequest request) {
        groupExhibitService.createGroupExhibit(principalDetails.getId(), request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "단체 전시 조회 (단체 전시 탭에서)")
    @GetMapping
    public ResponseEntity<GroupExhibitResponses> getAllGroupExhibit() {
        GroupExhibitResponses responses = groupExhibitService.getAllGroupExhibit();

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "단체 전시 상세 조회")
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupExhibitResponse> getGroupExhibit(@PathVariable Long groupId) {
        GroupExhibitResponse response = groupExhibitService.getGroupExhibit(groupId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "유저의 단체 전시 조회(유저 탭에서)")
    @GetMapping("/user")
    public ResponseEntity<GroupExhibitResponses> getAllUserGroupExhibit(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        GroupExhibitResponses responses = groupExhibitService.getAllUserGroupExhibit(principalDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "단체 전시 전시 추가")
    @PostMapping("/user/{groupId}/{exhibitId}")
    public ResponseEntity<?> addExhibit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PathVariable Long groupId, @PathVariable Long exhibitId
    ) {
        groupExhibitService.addExhibit(principalDetails.getId(), groupId, exhibitId);

        return ResponseEntity.ok().build();
    }
}
