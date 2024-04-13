package com.example.jolvre.post.service.impl;

import com.example.jolvre.post.entity.Post;
import com.example.jolvre.post.repository.PostRepository;
import com.example.jolvre.post.service.PostService;
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
