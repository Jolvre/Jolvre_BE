package com.example.jolvre.exhibition.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryImagesResponse;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponse;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponses;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUpdateRequest;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUploadRequest;
import com.example.jolvre.exhibition.service.DiaryService;
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

@Tag(name = "Diary", description = "일기장 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @Operation(summary = "일기장 업로드")
    @PostMapping(path = "/{exhibitId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadDiary(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @PathVariable Long exhibitId,
                                         @ModelAttribute DiaryUploadRequest request) {
        diaryService.uploadDiary(principalDetails.getId(), exhibitId, request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 일기장 조회")
    @GetMapping("/{exhibitId}")
    public ResponseEntity<DiaryInfoResponses> getAllDiary(
            @PathVariable Long exhibitId) {
        DiaryInfoResponses responses = diaryService.getAllDiaryInfo(exhibitId);

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "일기장 상세 조회")
    @GetMapping("/{exhibitId}/{diaryId}")
    public ResponseEntity<DiaryInfoResponse> getDiary(
            @PathVariable Long diaryId, @PathVariable Long exhibitId) {
        DiaryInfoResponse responses = diaryService.getDiaryInfo(diaryId, exhibitId);

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "일기장 업데이트")
    @PatchMapping(path = "/user/{exhibitId}/{diaryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDiary(@PathVariable Long diaryId, @PathVariable Long exhibitId,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @ModelAttribute DiaryUpdateRequest request) {

        diaryService.updateDiary(diaryId, exhibitId, principalDetails.getId(), request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "일기장 삭제")
    @DeleteMapping("/user/{exhibitId}/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable Long diaryId, @PathVariable Long exhibitId,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        diaryService.deleteDiary(diaryId, exhibitId, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "일기장 모든 사진 조회", description = "해당 전시의 모든 일기장 사진을 조회합니다")
    @GetMapping("/images/{exhibitId}")
    public ResponseEntity<DiaryImagesResponse> getDiaryImages(@PathVariable Long exhibitId) {

        DiaryImagesResponse response = diaryService.getDiaryImages(exhibitId);

        return ResponseEntity.ok().body(response);
    }
}
