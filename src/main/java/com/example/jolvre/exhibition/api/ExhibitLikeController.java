package com.example.jolvre.exhibition.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.exhibition.service.ExhibitLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "EXHIBIT LIKE", description = "전시 좋아요를 관리합니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exhibits")
public class ExhibitLikeController {
    private final ExhibitLikeService exhibitLikeService;

    @Operation(description = "전시 좋아요 기능", summary = "전시 좋아요수를 증가시킵니다")
    @PostMapping("/{exhibitId}/like")
    public ResponseEntity<Void> likeUpExhibit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @PathVariable Long exhibitId) {
        exhibitLikeService.likeUpExhibit(exhibitId, principalDetails.getId());

        return ResponseEntity.ok().build();
    }
}
