package com.example.jolvre.service.post;

import com.example.jolvre.domain.post.Comment;

public interface CommentService {
    public Comment upload(Comment comment);

    public Comment update(Comment comment);

    public void delete(Comment comment);
}
