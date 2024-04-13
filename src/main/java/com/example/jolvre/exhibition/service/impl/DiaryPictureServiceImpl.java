package com.example.jolvre.exhibition.service.impl;

import com.example.jolvre.exhibition.entity.DiaryPicture;
import com.example.jolvre.exhibition.repository.DiaryPictureRepository;
import com.example.jolvre.exhibition.service.DiaryPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryPictureServiceImpl implements DiaryPictureService {
    private final DiaryPictureRepository diaryPictureRepository;

    @Override
    public DiaryPicture upload(DiaryPicture diaryPicture) {
        return diaryPictureRepository.save(diaryPicture);
    }

    @Override
    public DiaryPicture update(DiaryPicture diaryPicture) {
        return null;
    }

    @Override
    public void delete(DiaryPicture diaryPicture) {
        diaryPictureRepository.delete(diaryPicture);
    }
}
