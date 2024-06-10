package com.example.jolvre.exhibition.service;

import com.example.jolvre.common.error.exhibition.DiaryNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryImagesResponse;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponse;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponses;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUpdateRequest;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUploadRequest;
import com.example.jolvre.exhibition.entity.Diary;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.repository.DiaryRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final ExhibitService exhibitService;
    private final ExhibitRepository exhibitRepository;
    private final S3Service s3Service;
    private final UserService userService;

    @Transactional
    public void uploadDiary(Long userId, Long exhibitId, DiaryUploadRequest request) {
        Exhibit exhibit = exhibitService.getExhibitByIdAndUserId(exhibitId, userId);
        User user = userService.getUserById(userId);

        Diary diary = Diary.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .exhibit(exhibit)
                .imageUrl(s3Service.uploadImage(request.getImage()))
                .user(user)
                .build();

        diaryRepository.save(diary);

        exhibit.addDiaries(diary);
        exhibitRepository.save(exhibit);
    }

    @Transactional
    public DiaryInfoResponses getAllDiaryInfo(Long exhibitId) {
        List<Diary> diaries = diaryRepository.findAllByExhibitId(exhibitId);
        
        return DiaryInfoResponses.builder()
                .diaryGetResponses(diaries
                        .stream()
                        .map(DiaryInfoResponse::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public DiaryInfoResponse getDiaryInfo(Long diaryId, Long exhibitId) {
        Diary diary = diaryRepository.findByIdAndExhibitId(diaryId, exhibitId)
                .orElseThrow(DiaryNotFoundException::new);

        return DiaryInfoResponse.toDTO(diary);
    }

    public void deleteDiary(Long diaryId, Long exhibitId, Long userId) {
        Diary diary = diaryRepository.findByIdAndExhibitIdAndUserId(diaryId, exhibitId,
                userId).orElseThrow(DiaryNotFoundException::new);

        diaryRepository.delete(diary);
    }

    public void updateDiary(Long diaryId, Long exhibitId, Long userId, DiaryUpdateRequest request) {
        Diary diary = diaryRepository.findByIdAndExhibitIdAndUserId(diaryId, exhibitId,
                userId).orElseThrow(DiaryNotFoundException::new);

        if (request.getImage() != null) {
            String imageUrl = s3Service.updateImage(request.getImage(), diary.getImageUrl());
            diary.updateImageUrl(imageUrl);
        }

        diary.update(request);

        diaryRepository.save(diary);
    }

    public DiaryImagesResponse getDiaryImages(Long exhibitId) {
        List<Diary> diaries = diaryRepository.findAllByExhibitId(exhibitId);
        List<String> images = new ArrayList<>();

        diaries.forEach(diary -> images.add(diary.getImageUrl()));

        return DiaryImagesResponse.builder().images(images).build();
    }
}
