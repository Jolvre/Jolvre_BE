package com.example.jolvre.post.dto;

import com.example.jolvre.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class postResponse {
    private Long postId;
    private String title;
    private String content;
    private String userName;
    private int view;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime last_modified_date;
//    private List<commentResponse> comments;

    public static postResponse findFromPost(Post post) {
        return new postResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getView(),
                post.getImageUrl(),
                post.getCreatedDate(),
                post.getLastModifiedDate()
        );
    }
}