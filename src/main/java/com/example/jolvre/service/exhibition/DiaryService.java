package com.example.jolvre.service.exhibition;

import com.example.jolvre.domain.exhibition.Diary;

public interface DiaryService {
    public Diary upload(Diary diary);

    public Diary update(Diary diary);

    public void delete(Diary diary);
}
