package com.example.jolvre.post.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany
    @JoinColumn(name = "comment_id") // 1:n
    private List<Comment> comments = new ArrayList<>();

    @Column
    private LocalTime uploadDate;

    @Column
    private LocalTime updateDate;

    @Builder
    public Post(User user, List<Comment> comments, String content, String title) {
        this.user = user;
        this.comments = comments;
        this.content = content;
        this.title = title;
        this.uploadDate = LocalTime.now();
        this.updateDate = LocalTime.now();
    }
}
