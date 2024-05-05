package com.example.jolvre.post.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalTime;

import lombok.*;
import org.joda.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")//N:1
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")//N:1
    private Post post;

    @Column
    private String userName;

    @Builder
    public Comment(String content, User user, Post post, String userName) {
        this.content = content;
        this.user = user;
        this.userName = userName;
    }
}
