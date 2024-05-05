package com.example.jolvre.post.service;


import com.example.jolvre.post.dto.commentRequest;
import com.example.jolvre.post.entity.Comment;
import com.example.jolvre.post.entity.Post;
import com.example.jolvre.post.repository.CommentRepository;
import com.example.jolvre.post.repository.PostRepository;
import com.example.jolvre.user.entity.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Builder
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

//    public void WriteComment(Long postId, commentRequest request, User loginUser) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("게시글 확인 불가"));
//
//        Comment comment = new Comment();
//        comment.setContent(request.getContent());
//        comment.setPost(post);
//        comment.setUser(loginUser);
////        comment.setUserName(loginUser.getNickname());
//
//        commentRepository.save(comment);
//        log.info("[comment] : 댓글 작성 완료");
//    }
//
//    public Comment upload(Comment comment) {
//        return commentRepository.save(comment);
//    }
//    //댓글 조회에 PrincipatDetails로 불러온 User loginUser에서 loginUser.getName()으로
//    //commentResponse의 userName 설정해주기
//
//    public Comment update(Comment comment) {
//        return null;
//    }
//
//    public void delete(Comment comment) {
//        commentRepository.delete(comment);
//    }

}