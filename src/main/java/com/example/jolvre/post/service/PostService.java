package com.example.jolvre.post.service;

import com.example.jolvre.post.dto.PostDto;
import com.example.jolvre.post.entity.Post;
import com.example.jolvre.post.repository.PostRepository;
import com.example.jolvre.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Builder
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public void upload(PostDto.PostUploadRequest request, User loginuser)
    {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(loginuser)
                .build();

        postRepository.save(post);
        log.info("[post] : {}님의 {} 업로드 성공", loginuser.getName(), post.getTitle());
    }

    public List<Post> getAllPost(User user) {
        return postRepository.findAllByUserId(user.getId());
    }

    public PostDto.PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);

        return PostDto.PostResponse.builder()
                .title(Objects.requireNonNull(post).getTitle())
                .content(post.getContent())
                .build();
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
