package com.example.jolvre.exhibition.repository;

import com.example.jolvre.exhibition.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
