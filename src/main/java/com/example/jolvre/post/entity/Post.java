package com.example.jolvre.post.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id") // n:1
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostImage> postImages = new ArrayList<>();

    @Column
    private int view;

    @Column
    private Category category;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JsonIgnoreProperties({"post"})
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(User user, String content, String title, int view, Category category) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.view = 0;
        this.category = category;
    }

    public void addImage(PostImage postImage) {
        postImage.addPost(this);
        this.postImages.add(postImage);
    }

    public List<String> getImageUrls() {
        List<String> urls = new ArrayList<>();

        this.postImages.forEach(
                image -> urls.add(image.getUrl())
        );
        return urls;
    }
}
