package com.example.jolvre.service.exhibition;

import com.example.jolvre.domain.exhibition.DiaryPicture;

public interface DiaryPictureService {

    public DiaryPicture upload(DiaryPicture diaryPicture);

    public DiaryPicture update(DiaryPicture diaryPicture);

    public void delete(DiaryPicture diaryPicture);
}
