package com.example.jolvre.post.service;

import com.example.jolvre.post.dto.postRequest;
import com.example.jolvre.post.dto.postResponse;
import com.example.jolvre.post.entity.Post;
import com.example.jolvre.post.repository.PostRepository;
import com.example.jolvre.user.entity.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@Builder
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public void upload(postRequest request, User loginuser)
    {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(loginuser);

        postRepository.save(post);
        log.info("[post] : {} 업로드 성공", post.getTitle());
    }

    public Page<postResponse> getAllPost(PageRequest pageable) {
        Page<Post> postList = postRepository.findAll(pageable);
        return postList.map(postResponse::findFromPost);
    }

    public List<Post> getPostsByUserId(Long userId) {
        log.info("[post] : {}의 모든 게시글 조회", userId);
        return postRepository.findAllByUserId(userId);
    }

    public postResponse getPostById(Long postId) {
        Post post = findById(postId);

        log.info("[post] : {} 불러오기", Objects.requireNonNull(post).getTitle());
        return postResponse.findFromPost(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Does not exist post"));
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
        log.info("[post] : {} 게시글 삭제 완료", postId);
    }

    public void updatePost(postRequest request, Long postId, User loginuser) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());

        postRepository.save(existingPost);
        log.info("[post] : {} 게시글 수정 완료", request.getTitle());
    }
}
