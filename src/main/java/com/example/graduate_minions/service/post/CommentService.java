package com.example.graduate_minions.service.post;

import com.example.graduate_minions.domain.post.Comment;

public interface CommentService {
    public Comment upload();

    public Comment update();

    public Comment delete();
}
