package com.example.jolvre.post.service.config;

import com.example.jolvre.post.repository.CommentRepository;
import com.example.jolvre.post.repository.PostRepository;
import com.example.jolvre.post.service.CommentService;
import com.example.jolvre.post.service.PostService;
import com.example.jolvre.post.service.impl.CommentServiceImpl;
import com.example.jolvre.post.service.impl.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PostConfig {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Bean
    public PostService postService() {
        return new PostServiceImpl(postRepository);
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceImpl(commentRepository);
    }
}
