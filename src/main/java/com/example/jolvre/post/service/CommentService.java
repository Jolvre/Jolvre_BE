package com.example.jolvre.post.service;

import com.example.jolvre.post.entity.Comment;

public interface CommentService {
    public Comment upload(Comment comment);

    public Comment update(Comment comment);

    public void delete(Comment comment);
}
