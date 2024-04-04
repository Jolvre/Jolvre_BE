package com.example.jolvre.domain.exhibition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private long id;

    @Column(nullable = false)
    private String content;

    @OneToOne // 1:1
    @JoinColumn(name = "diary_picture_id")
    private DiaryPicture diaryPicture;

    @Column
    private String title;

    @Column
    private LocalTime uploadDate;

    public Diary(String content, DiaryPicture diaryPicture) {
        this.content = content;
        this.diaryPicture = diaryPicture;
    }
}
