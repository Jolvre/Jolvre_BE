package com.example.jolvre.repository.exhibition;

import com.example.jolvre.domain.exhibition.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
