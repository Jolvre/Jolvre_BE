package com.example.jolvre.post.entity;

import com.example.jolvre.common.entity.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PostImage extends Image {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostImage(String url, Post post) {
        super(url);
        this.post = post;
    }
}
