package com.example.jolvre.exhibition.entity;

import com.example.jolvre.common.entity.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DiaryImage extends Image {
    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    public DiaryImage(String url, Diary diary) {
        super(url);
        this.diary = diary;
    }
}
