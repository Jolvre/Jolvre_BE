package com.example.jolvre.post.service;


import com.example.jolvre.common.error.comment.CommentNotFoundException;
import com.example.jolvre.common.error.post.PostNotFoundException;
import com.example.jolvre.common.error.user.UserAccessDeniedException;
import com.example.jolvre.common.firebase.Service.FCMService;
import com.example.jolvre.post.dto.commentRequest;
import com.example.jolvre.post.dto.commentResponse;
import com.example.jolvre.post.entity.Comment;
import com.example.jolvre.post.entity.Post;
import com.example.jolvre.post.repository.CommentRepository;
import com.example.jolvre.post.repository.PostRepository;
import com.example.jolvre.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
@Builder
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final FCMService FCMService;

    public void writeComment(Long postId, commentRequest request, User user)
            throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(post);
        comment.setUser(user);
        comment.setUserName(user.getNickname());

        commentRepository.save(comment);
        log.info("[comment] : 댓글 작성 완료");

        String title = user.getNickname() + "님이 댓글을 작성하셨습니다";
        String body = request.getContent();
        String targetToken = FCMService.getTargetToken(post.getUser());

        FCMService.sendMessageTo(targetToken, title, body);
    }

    public Page<commentResponse> findAllComment(Pageable pageable, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Page<Comment> commentList = commentRepository.findByPost(post, pageable);
        return commentList.map(commentResponse::findFromComment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(CommentNotFoundException::new);

        if (!Objects.equals(comment.getUser().getId(), user.getId())) {
            log.info("[comment] : 댓글 삭제 권한이 없습니다");
            throw new UserAccessDeniedException();
        }
        else {
            commentRepository.deleteById(commentId);
            log.info("[comment] : {} 댓글 삭제 완료", commentId);
        }
    }
}