package com.example.jolvre.service.post.impl;

import com.example.jolvre.domain.post.Post;
import com.example.jolvre.repository.post.PostRepository;
import com.example.jolvre.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post upload(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post Update(Post post) {
        return null;
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
