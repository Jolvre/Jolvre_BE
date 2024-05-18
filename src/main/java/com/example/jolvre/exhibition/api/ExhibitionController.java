package com.example.jolvre.exhibition.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitInfoResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponse;
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
    public ResponseEntity<?> uploadExhibit(@ModelAttribute ExhibitUploadRequest request,
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

    @Operation(summary = "전시 상세 조회")
    @GetMapping("/{exhibitId}")
    public ResponseEntity<ExhibitResponse> getExhibit(@PathVariable Long exhibitId) {
        ExhibitResponse response = exhibitService.getExhibitInfo(exhibitId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "바동기 전시 업데이트 테스트")
    @PatchMapping(path = "/user/{exhibitId}/testAsync", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateExhibitAsync(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                   @ModelAttribute ExhibitUploadRequest request) {

        exhibitService.uploadAsync(request, principalDetails.getId());
    }
}
