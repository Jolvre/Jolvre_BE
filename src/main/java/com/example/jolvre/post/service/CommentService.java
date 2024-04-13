package com.example.jolvre.post.service;

import com.example.jolvre.post.entity.Comment;
import com.example.jolvre.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment upload(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment) {
        return null;
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
