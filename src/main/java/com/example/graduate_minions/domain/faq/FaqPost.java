package com.example.graduate_minions.domain.faq;

import com.example.graduate_minions.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class FaqPost {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "faq_post_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // n:1
    private User user;

    @OneToMany
    @JoinColumn(name = "faq_comment_id") // 1:n
    private List<FaqComment> faqComments = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Builder
    public FaqPost(User user, List<FaqComment> faqComments, String content, String title) {
        this.user = user;
        this.faqComments = faqComments;
        this.content = content;
        this.title = title;
    }
}
