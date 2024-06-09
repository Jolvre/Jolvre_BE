package com.example.jolvre.exhibition.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentInfoResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentUpdateRequest;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentUploadRequest;
import com.example.jolvre.exhibition.service.ExhibitCommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/exhibit")
public class ExhibitionCommentController {
    private final ExhibitCommentService exhibitCommentService;

    @Operation(summary = "해당 전시 코멘트 업로드", description = "해당 전시에 코멘트를 업로드 한다")
    @PostMapping("/{exhibitId}/comment")
    public ResponseEntity<Void> uploadComment(@PathVariable Long exhibitId,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @RequestBody ExhibitCommentUploadRequest request) {
        exhibitCommentService.uploadComment(exhibitId, principalDetails.getId(), request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "해당 전시 모든 코멘트 조회", description = "해당 전시에 코멘트를 입력한다")
    @GetMapping("/{exhibitId}/comment")
    public ResponseEntity<ExhibitCommentInfoResponses> getAllComment(@PathVariable Long exhibitId) {
        ExhibitCommentInfoResponses response = exhibitCommentService.getAllCommentInfo(exhibitId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "코멘트 수정", description = "해당 전시에 특정 코멘트를 수정한다")
    @PatchMapping("/{exhibitId}/comment/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long exhibitId,
                                              @PathVariable Long commentId,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @RequestBody ExhibitCommentUpdateRequest request) {
        exhibitCommentService.updateComment(commentId, principalDetails.getId(), request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "코멘트 삭제", description = "해당 전시에 특정 코멘트를 삭제한다")
    @DeleteMapping("/{exhibitId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long exhibitId,
                                              @PathVariable Long commentId,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        exhibitCommentService.deleteComment(commentId, principalDetails.getId());

        return ResponseEntity.ok().build();
    }
}
