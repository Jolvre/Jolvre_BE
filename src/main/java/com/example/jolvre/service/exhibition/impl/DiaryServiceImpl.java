package com.example.jolvre.service.exhibition.impl;

import com.example.jolvre.domain.exhibition.Diary;
import com.example.jolvre.repository.exhibition.DiaryRepository;
import com.example.jolvre.service.exhibition.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;


    @Override
    public Diary upload(Diary diary) {
        return diaryRepository.save(diary);
    }

    @Override
    public Diary update(Diary diary) {
        return null;
    }

    @Override
    public void delete(Diary diary) {
        diaryRepository.delete(diary);
    }
}
