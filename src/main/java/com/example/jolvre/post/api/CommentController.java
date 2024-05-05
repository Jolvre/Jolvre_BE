package com.example.jolvre.post.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.post.dto.commentRequest;
import com.example.jolvre.post.repository.CommentRepository;
import com.example.jolvre.post.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpHeaders;
import java.util.List;

@Tag(name = "댓글 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class CommentController {

//    private final CommentService commentService;
//
//    @Operation(summary = "댓글 작성")
//    @PostMapping("/{postId}/comment")
//    public ResponseEntity<?> uploadComment(@PathVariable("postId") Long postId, @RequestBody commentRequest request,
//                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        commentService.WriteComment(postId, request, principalDetails.getUser());
//
//        return ResponseEntity.ok().build();
//    }
}
