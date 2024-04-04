package com.example.jolvre.service.post.impl;

import com.example.jolvre.domain.post.Comment;
import com.example.jolvre.repository.post.CommentRepository;
import com.example.jolvre.service.post.CommentService;
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
