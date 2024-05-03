package com.example.jolvre.exhibition.repository;

import com.example.jolvre.exhibition.entity.DiaryImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    List<DiaryImage> findAllByDiaryId(Long id);
}
