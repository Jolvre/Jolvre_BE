package com.example.jolvre.exhibition.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ExhibitComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exhibit_comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "exhibit_id")
    private Exhibit exhibit;

    @Column
    private String content;

    @Builder
    public ExhibitComment(User user, Exhibit exhibit, String content) {
        this.user = user;
        this.exhibit = exhibit;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
