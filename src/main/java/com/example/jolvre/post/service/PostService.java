package com.example.jolvre.post.service;

import com.example.jolvre.post.entity.Post;
import com.example.jolvre.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post upload(Post post) {
        return postRepository.save(post);
    }

    public Post Update(Post post) {
        return null;
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
