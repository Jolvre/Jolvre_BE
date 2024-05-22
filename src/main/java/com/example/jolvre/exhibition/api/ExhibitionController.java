package com.example.jolvre.exhibition.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentInfoResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentUpdateRequest;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentUploadRequest;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInfoResponse;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInfoResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInvitationResponse;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUpdateRequest;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUploadRequest;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUploadResponse;
import com.example.jolvre.exhibition.service.ExhibitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Exhibit", description = "졸업 작품 전시 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/exhibit")
public class ExhibitionController {
    private final ExhibitService exhibitService;

    @Operation(summary = "전시 업로드")
    @PostMapping(path = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExhibitUploadResponse> uploadExhibit(@ModelAttribute ExhibitUploadRequest request,
                                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ExhibitUploadResponse response = exhibitService.uploadExhibit(request, principalDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "유저 전체 전시 조회 (유저탭에서)")
    @GetMapping("/user")
    public ResponseEntity<ExhibitInfoResponses> getAllUserExhibit(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ExhibitInfoResponses response = exhibitService.getAllUserExhibitInfo(principalDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "전시 삭제")
    @DeleteMapping("/user/{exhibitId}")
    public void deleteExhibit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              @PathVariable Long exhibitId) {
        exhibitService.deleteExhibit(exhibitId, principalDetails.getId());
    }

    @Operation(summary = "전시 업데이트")
    @PatchMapping(path = "/user/{exhibitId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateExhibit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              @PathVariable Long exhibitId, @ModelAttribute ExhibitUpdateRequest request) {

        exhibitService.updateExhibit(exhibitId, principalDetails.getId(), request);
    }

    @Operation(summary = "전시 배포")
    @PostMapping("/user/{exhibitId}/distribute")
    public ResponseEntity<?> distributeExhibit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @PathVariable Long exhibitId) {
        exhibitService.distributeExhibit(exhibitId, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    //todo : 페이징 필요
    @Operation(summary = "전체 전시 조회 (전시탭에서)")
    @GetMapping
    public ResponseEntity<ExhibitInfoResponses> getAllExhibit() {
        ExhibitInfoResponses responses = exhibitService.getAllExhibitInfo();

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "전체 작품 종류별 전시 조회 (전시탭에서)")
    @GetMapping("/exhibits/{workType}")
    public ResponseEntity<ExhibitInfoResponses> getAllExhibit(@PathVariable String workType) {
        ExhibitInfoResponses responses = exhibitService.getAllExhibitInfoByWorkType(workType);

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "전시 상세 조회")
    @GetMapping("/{exhibitId}")
    public ResponseEntity<ExhibitInfoResponse> getExhibit(@PathVariable Long exhibitId) {
        ExhibitInfoResponse response = exhibitService.getExhibitInfo(exhibitId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "바동기 전시 업데이트 테스트")
    @PatchMapping(path = "/user/{exhibitId}/testAsync", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateExhibitAsync(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                   @ModelAttribute ExhibitUploadRequest request) {

        exhibitService.uploadAsync(request, principalDetails.getId());
    }

    @Operation(summary = "초대장 생성", description = "해당 전시에 맞는 초대장을 생성해준다")
    @GetMapping("/{exhibitId}/invitation")
    public ResponseEntity<ExhibitInvitationResponse> createInvitation(@PathVariable Long exhibitId) {
        ExhibitInvitationResponse response = exhibitService.createInvitation(exhibitId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "해당 전시 코멘트 업로드", description = "해당 전시에 코멘트를 업로드 한다")
    @PostMapping("/{exhibitId}/comment")
    public ResponseEntity<?> uploadComment(@PathVariable Long exhibitId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails,
                                           @RequestBody ExhibitCommentUploadRequest request) {
        exhibitService.uploadComment(exhibitId, principalDetails.getId(), request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "해당 전시 모든 코멘트 조회", description = "해당 전시에 코멘트를 입력한다")
    @GetMapping("/{exhibitId}/comment")
    public ResponseEntity<ExhibitCommentInfoResponses> getAllComment(@PathVariable Long exhibitId) {
        ExhibitCommentInfoResponses response = exhibitService.getAllCommentInfo(exhibitId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "코멘트 수정", description = "해당 전시에 특정 코멘트를 수정한다")
    @PatchMapping("/{exhibitId}/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long exhibitId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails,
                                           @RequestBody ExhibitCommentUpdateRequest request) {
        exhibitService.updateComment(commentId, principalDetails.getId(), request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "코멘트 삭제", description = "해당 전시에 특정 코멘트를 삭제한다")
    @DeleteMapping("/{exhibitId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long exhibitId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        exhibitService.deleteComment(commentId, principalDetails.getId());

        return ResponseEntity.ok().build();
    }
}
