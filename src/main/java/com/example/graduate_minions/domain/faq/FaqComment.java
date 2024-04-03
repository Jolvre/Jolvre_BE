package com.example.graduate_minions.domain.faq;

import com.example.graduate_minions.domain.post.Post;
import com.example.graduate_minions.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class FaqComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_comment_id")
    private long id;

    @Column(nullable = false)
    private String content;


    @ManyToOne
    @JoinColumn(name = "user_id")//N:1
    private User user;


    @ManyToOne
    @JoinColumn(name = "faq_post_id")//N:1
    private Post post;

    @Builder
    public FaqComment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
    }
}
