package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.entity.DiaryPicture;
import com.example.jolvre.exhibition.repository.DiaryPictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryPictureService {
    private final DiaryPictureRepository diaryPictureRepository;


    public DiaryPicture upload(DiaryPicture diaryPicture) {
        return diaryPictureRepository.save(diaryPicture);
    }


    public DiaryPicture update(DiaryPicture diaryPicture) {
        return null;
    }

    
    public void delete(DiaryPicture diaryPicture) {
        diaryPictureRepository.delete(diaryPicture);
    }
}
