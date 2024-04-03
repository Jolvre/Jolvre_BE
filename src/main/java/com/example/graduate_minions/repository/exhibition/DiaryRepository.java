package com.example.graduate_minions.repository.exhibition;

import com.example.graduate_minions.domain.exhibition.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
