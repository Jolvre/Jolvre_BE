package com.example.jolvre.post.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.post.dto.postRequest;
import com.example.jolvre.post.dto.postResponse;
import com.example.jolvre.post.entity.Post;

import com.example.jolvre.post.service.PostService;
import com.example.jolvre.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "커뮤니티 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;
//    private final CommentService commentService;

    //게시글 작성
    @PostMapping("/upload")
    @Operation(summary = "게시글 작성", description = "사용자id(userid 아님), 제목, 내용 입력")
    public ResponseEntity<?> Post(@RequestBody postRequest request,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postService.upload(request, principalDetails.getUser());
        return ResponseEntity.ok().build();
    }

    //전체 게시글 조회
    @Operation(summary = "전체 게시글 조회")
    @GetMapping("/list")
    public List<Post> getAllPosts() {
        return postService.getAllPost();
    }

    //특정 유저가 작성한 모든 글 조회
    @Operation(summary = "유저의 게시글 조회")
    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUserId(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return postService.getPostsByUserId(principalDetails.getUser().getId());
    }

    //특정 게시글 조회
    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{postId}")
    public postResponse getPost(@PathVariable("postId") Long postId) {
        return postService.getPost(postId);
    }

    //특정 게시글 삭제
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("postId") Long postId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postService.delete(postId);
    }

    //특정 게시글 수정
    @PostMapping("/update/{postId}")
    @Operation(summary = "특정 게시글 수정")
    public ResponseEntity<?> updatePost(@RequestBody postRequest request, @PathVariable("postId") Long postId,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postService.updatePost(request, postId, principalDetails.getUser());
        return ResponseEntity.ok().build();
    }


//이놈이 문제다
//    @GetMapping()
//    @Operation(summary = "제목 키워드 (str)로 검색", description = "키워드 입력, pageable에 page 설정, size 갯수 설정, sort는 id로")
//    public Page<PostResponse> searchByKeyword(@PathVariable String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
//        return postService.searchByKeyword(keyword, pageable);
//    }
//
//    @PostMapping("/api/comment/upload")
//    private Comment uploadComment(Comment comment) {
//        return commentService.upload(comment);
//    }
//
//    @PostMapping("/api/comment/update")
//    private Comment updateComment(Comment comment) {
//        return commentService.update(comment);
//    }
//
//    @PostMapping("/api/comment/delete")
//    private void deleteComment(Comment comment) {
//        CommentService.delete(comment);
//    }
//
//}
}