package com.example.jolvre.exhibition.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = @Index(name = "idx_exhibit", columnList = "exhibit_id"))
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String imageUrl;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibit_id")
    private Exhibit exhibit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
