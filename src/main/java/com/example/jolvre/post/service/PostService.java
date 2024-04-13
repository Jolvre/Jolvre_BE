package com.example.jolvre.post.service;

import com.example.jolvre.post.entity.Post;

public interface PostService {
    public Post upload(Post post);

    public Post Update(Post post);

    public void delete(Post post);


}
