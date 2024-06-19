package com.example.jolvre.exhibition.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.exhibition.service.ExhibitLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exhibits")
public class ExhibitLikeController {
    private final ExhibitLikeService exhibitLikeService;

    @PostMapping("/{exhibitId}/like")
    public ResponseEntity<Void> likeUpExhibit(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                              @PathVariable Long exhibitId) {
        exhibitLikeService.likeUpExhibit(exhibitId, principalDetails.getId());

        return ResponseEntity.ok().build();
    }
}
