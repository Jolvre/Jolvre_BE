package com.example.jolvre.post.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.post.dto.postRequest;
import com.example.jolvre.post.dto.postResponse;
import com.example.jolvre.post.entity.Post;

import com.example.jolvre.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Operation(summary = "전체 게시글 조회")
    @GetMapping("/list")
    public List<Post> getAllPosts() {
        return postService.getAllPost();
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{postId}")
    public postResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.delete(postId);
    }
    //게시글 수정
    //*
//    @PostMapping("/{id}")
//    @Operation(summary = "특정 게시글 수정 (id)", description = "파라미터 id에 수정하고자 하는 게시글 id 입력 -> 사용자 id(userid 아님), 제목, 내용 출력")
//    public ResponseEntity<?> updatePost(@PathVariable("id") long id, @RequestBody PostRequest request) {
//        try {
//            PostService.updatePost(id, request);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .headers(getHeaders)
//                    .body("게시글 수정 완료");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .headers(getHeaders)
//                    .body("게시글 수정 권한이 없습니다");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .headers(getHeaders)
//                    .body("게시판 정보가 없습니다");
//        }
//    }

//    //전체 게시글 조회
//    @GetMapping("/list")
//    @Operation(summary = "전체 게시글 조회", description = "게시글 id, 제목, 내용, 사용자id, 사용자명, 작성일 전체 출력")
//    public ResponseEntity<List<PostResponse>> findAllPosts() {
//        List<PostResponse> allPosts = postService.findAllPosts();
//        return ResponseEntity.status(HttpStatus.OK).body(allPosts);
//    }

//    //게시글 상세 조회
//    @GetMapping("/{id}")
//    @Operation(summary = "게시글 상세 조회 (id로)", description = "파라미터 id에 열람하고자 하는 게시글 id 입력 -> 게시글 id, 제목, 내용, 사용자id, 사용자명, 작성일 출력")
//    public ResponseEntity<?> getPostDetails(@PathVariable Long postId) {
//        PostResponse post = postService.getPostDetails(postId);
//        return post != null ? ResponseEntity.status(HttpStatus.OK).body(post) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글이 존재하지 않습니다");
//    }
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