package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.entity.Diary;
import com.example.jolvre.exhibition.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public Diary upload(Diary diary) {
        return diaryRepository.save(diary);
    }

    public Diary update(Diary diary) {
        return null;
    }

    public void delete(Diary diary) {
        diaryRepository.delete(diary);
    }
}
