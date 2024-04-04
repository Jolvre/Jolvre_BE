package com.example.jolvre.domain.exhibition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class DiaryPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_picture_id")
    private long id;

    @Column
    private String originalName;

    @Column
    private String storedName;
    @Column
    private long fileSize;

    @Builder
    public DiaryPicture(String originalName, String storedName, long fileSize) {
        this.originalName = originalName;
        this.storedName = storedName;
        this.fileSize = fileSize;
    }
}
