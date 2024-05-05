package com.example.jolvre.post.dto;

import com.example.jolvre.post.entity.Post;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class postResponse {
    private Long postId;
    private String title;
    private String content;
//    private List<commentResponse> comments;

    public static postResponse findFromPost(Post post) {
        return new postResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent()
        );
    }
}