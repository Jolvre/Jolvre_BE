package com.example.jolvre.domain.post;

import com.example.jolvre.domain.BaseTimeEntity;
import com.example.jolvre.domain.user.User;
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
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // n:1
    private User user;

    @OneToMany
    @JoinColumn(name = "comment_id") // 1:n
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Column
    private LocalTime uploadDate;

    @Builder
    public Post(User user, List<Comment> comments, String content, String title) {
        this.user = user;
        this.comments = comments;
        this.content = content;
        this.title = title;
    }
}
