package com.example.jolvre.service.exhibition.impl;

import com.example.jolvre.domain.exhibition.DiaryPicture;
import com.example.jolvre.repository.exhibition.DiaryPictureRepository;
import com.example.jolvre.service.exhibition.DiaryPictureService;
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
