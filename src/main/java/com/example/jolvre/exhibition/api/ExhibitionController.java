package com.example.jolvre.exhibition.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponse;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUploadRequest;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Operation(summary = "전시 업로드")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadExhibit(@ModelAttribute ExhibitUploadRequest request,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        exhibitService.upload(request, principalDetails.getId());

        userRepository.delete(principalDetails.getUser());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 전체 전시 조회 (유저탭에서)")
    @GetMapping("/user")
    public ResponseEntity<ExhibitResponses> getAllUserExhibit(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ExhibitResponses response = exhibitService.getAllUserExhibit(principalDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "전체 전시 조회 (전시탭에서)") //페이징 필요
    @GetMapping
    public ResponseEntity<ExhibitResponses> getAllExhibit(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ExhibitResponses responses = exhibitService.getAllExhibit();

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "전시 상세 조회")
    @GetMapping("/{exhibitId}")
    public ResponseEntity<ExhibitResponse> getExhibit(@PathVariable Long exhibitId) {
        ExhibitResponse response = exhibitService.getExhibit(exhibitId);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "전시 삭제")
    @DeleteMapping("/{exhibitId}")
    public void deleteExhibit(@PathVariable Long exhibitId) {
        exhibitService.delete(exhibitId);
    }

    @Operation(summary = "전시 업데이트")
    @PatchMapping("/{exhibitId}")
    public void updateExhibit(@PathVariable long exhibitId) {

    }

}
