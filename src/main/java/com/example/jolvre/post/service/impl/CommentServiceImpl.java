package com.example.jolvre.post.service.impl;

import com.example.jolvre.post.entity.Comment;
import com.example.jolvre.post.repository.CommentRepository;
import com.example.jolvre.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment upload(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return null;
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
