package com.example.jolvre.service.post;

import com.example.jolvre.domain.post.Post;

public interface PostService {
    public Post upload(Post post);

    public Post Update(Post post);

    public void delete(Post post);


}
