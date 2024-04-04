package com.example.jolvre.service.post.config;

import com.example.jolvre.repository.post.CommentRepository;
import com.example.jolvre.repository.post.PostRepository;
import com.example.jolvre.service.post.CommentService;
import com.example.jolvre.service.post.PostService;
import com.example.jolvre.service.post.impl.CommentServiceImpl;
import com.example.jolvre.service.post.impl.PostServiceImpl;
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
