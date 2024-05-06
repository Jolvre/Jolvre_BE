package com.example.jolvre.post.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.post.dto.commentRequest;
import com.example.jolvre.post.dto.commentResponse;
import com.example.jolvre.post.repository.CommentRepository;
import com.example.jolvre.post.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpHeaders;
import java.util.List;

@Tag(name = "Comment", description = "커뮤니티 댓글 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성")
    @PostMapping("/{postId}/upload")
    public ResponseEntity<?> uploadComment(@PathVariable("postId") Long postId, @RequestBody commentRequest request,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.writeComment(postId, request, principalDetails.getUser());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    @Operation(summary = "{postId} 게시글의 댓글 불러오기")
    public ResponseEntity<Page<commentResponse>> getAllComment(
            @PathVariable("postId") Long postId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Pageable pageable) {
        pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").ascending());
        Page<commentResponse> commentList = commentService.findAllComment(pageable, postId);

        return ResponseEntity.status(HttpStatus.OK).body(commentList);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public void deleteComment(@PathVariable("commentId") Long commentId,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.deleteComment(commentId);
    }
}
