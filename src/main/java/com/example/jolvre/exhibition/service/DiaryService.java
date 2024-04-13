package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.entity.Diary;

public interface DiaryService {
    public Diary upload(Diary diary);

    public Diary update(Diary diary);

    public void delete(Diary diary);
}
