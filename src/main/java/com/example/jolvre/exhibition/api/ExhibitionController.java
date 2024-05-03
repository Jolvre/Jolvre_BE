package com.example.jolvre.exhibition.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitResponse;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitUploadRequest;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.service.ExhibitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
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
    private final S3Service s3Service;


    @Operation(summary = "전시 업로드")
    @PostMapping
    public ResponseEntity<?> uploadExhibit(@ParameterObject @ModelAttribute ExhibitUploadRequest request,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {

        exhibitService.upload(request, principalDetails.getUser(), s3Service.uploadImageList(request.getImages()));

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전체 전시 조회")
    @GetMapping
    public List<Exhibit> getAllExhibit(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return exhibitService.getAllExhibit(principalDetails.getUser());
    }

    @Operation(summary = "전시 상세 조회")
    @GetMapping("/{exhibitId}")
    public ExhibitResponse getExhibit(@PathVariable long exhibitId) {
        return exhibitService.getExhibit(exhibitId);
    }

    @Operation(summary = "전시 삭제")
    @DeleteMapping("/{exhibitId}")
    public void deleteExhibit(@PathVariable long exhibitId) {
        exhibitService.delete(exhibitId);
    }

    @Operation(summary = "전시 업데이트")
    @PatchMapping("/{exhibitId}")
    public void updateExhibit(@PathVariable long exhibitId) {

    }

}
