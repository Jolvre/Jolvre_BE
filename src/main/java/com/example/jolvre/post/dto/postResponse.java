package com.example.jolvre.post.dto;

import com.example.jolvre.post.entity.Category;
import com.example.jolvre.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
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
    private String userName;
    private int view;
    private LocalDateTime createdDate;
    private LocalDateTime last_modified_date;
    private Category category;
    private List<String> imagesUrl;

    public static postResponse toDTO(Post post) {
        return new postResponse(post.getPostId(), post.getTitle(),
                post.getContent(), post.getUser().getNickname(),
                post.getView(), post.getCreatedDate(),
                post.getLastModifiedDate(), post.getCategory(),
                post.getImageUrls()
        );
    }
}