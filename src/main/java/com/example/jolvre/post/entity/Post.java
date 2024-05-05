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

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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


//    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    @JoinColumn(name = "comment_id") // 1:n
//    @JsonIgnoreProperties({"post"})
//    private List<Comment> comments = new ArrayList<>();

//    @Column
//    private LocalDateTime createdDate;
//
//    @Column
//    private LocalDateTime updateDate;

    @Builder
    public Post(User user, String content, String title) {
        this.user = user;
        //this.comments = comments;
        this.title = title;
        this.content = content;
    }
}
