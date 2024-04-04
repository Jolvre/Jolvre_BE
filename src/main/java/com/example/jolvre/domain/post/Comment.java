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
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")//N:1
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")//N:1
    private Post post;

    @Column
    private String title;

    @Column
    private LocalTime uploadDate;

    @Builder
    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
    }
}
