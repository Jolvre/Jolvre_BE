package com.example.jolvre.exhibition.api;

import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponse;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponses;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUploadRequest;
import com.example.jolvre.exhibition.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/exhibit/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @Operation(summary = "일기장 업로드")
    @PostMapping("/{exhibitId}")
    public ResponseEntity<?> uploadDiary(@PathVariable Long exhibitId,
                                         @ParameterObject @ModelAttribute DiaryUploadRequest request) {
        diaryService.upload(exhibitId, request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 일기장 조회")
    @GetMapping("/{exhibitId}")
    public ResponseEntity<DiaryInfoResponses> getAllDiary(@PathVariable Long exhibitId) {
        DiaryInfoResponses responses = diaryService.getAllDiaryInfo(exhibitId);

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "일기장 상세 조회")
    @GetMapping("/{exhibitId}/{diaryId}")
    public ResponseEntity<DiaryInfoResponse> getDiary(@PathVariable Long diaryId, @PathVariable Long exhibitId) {
        DiaryInfoResponse responses = diaryService.getDiaryInfo(diaryId, exhibitId);

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "일기장 업데이트")
    @PatchMapping("/{exhibitId}/{diaryId}")
    public ResponseEntity<?> updateDiary(@PathVariable Long diaryId) {

        diaryService.delete(diaryId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "일기장 삭제")
    @DeleteMapping("/{exhibitId}/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable Long diaryId) {

        diaryService.delete(diaryId);

        return ResponseEntity.ok().build();
    }
}
