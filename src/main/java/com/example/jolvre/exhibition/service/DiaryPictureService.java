package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.entity.DiaryPicture;

public interface DiaryPictureService {

    public DiaryPicture upload(DiaryPicture diaryPicture);

    public DiaryPicture update(DiaryPicture diaryPicture);

    public void delete(DiaryPicture diaryPicture);
}
