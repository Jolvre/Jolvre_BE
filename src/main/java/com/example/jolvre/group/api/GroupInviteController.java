package com.example.jolvre.group.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.group.dto.GroupInviteDTO.InviteRequest;
import com.example.jolvre.group.dto.GroupInviteDTO.InviteResponses;
import com.example.jolvre.group.entity.InviteState;
import com.example.jolvre.group.service.GroupInviteService;
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

@Tag(name = "Group Invite", description = "단체전시 초대 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/group/invite")
public class GroupInviteController {
    private final GroupInviteService groupInviteService;

    @Operation(summary = "유저 초대1")
    @PostMapping("/{groupId}")
    public ResponseEntity<?> inviteUser(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PathVariable Long groupId,
                                        @RequestBody InviteRequest request) {
        groupInviteService.inviteUser(principalDetails.getId(), request.getNickname(), groupId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "자신의 초대 현황 조회")
    @GetMapping("/user")
    public ResponseEntity<InviteResponses> getAllInvite(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        InviteResponses responses = groupInviteService.getAllInvite(principalDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "자신의 초대 수락 Or 거절")
    @PostMapping("/user/{inviteId}/{inviteState}")
    public ResponseEntity<?> checkInvite(@PathVariable Long inviteId,
                                         @PathVariable InviteState inviteState,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        groupInviteService.checkInviteStatus(inviteId, inviteState, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

}
