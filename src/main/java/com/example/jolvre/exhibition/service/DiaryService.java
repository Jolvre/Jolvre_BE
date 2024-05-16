package com.example.jolvre.exhibition.service;

import com.example.jolvre.common.error.exhibition.DiaryNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponse;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryInfoResponses;
import com.example.jolvre.exhibition.dto.DiaryDTO.DiaryUploadRequest;
import com.example.jolvre.exhibition.entity.Diary;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.repository.DiaryRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import jakarta.transaction.Transactional;
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
    private final ExhibitRepository exhibitRepository;
    private final S3Service s3Service;

    @Transactional
    public void upload(Long exhibitId, DiaryUploadRequest request) {
        Exhibit exhibit = exhibitRepository.findById(exhibitId).orElseThrow(DiaryNotFoundException::new);

        Diary diary = Diary.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .exhibit(exhibit)
                .imageUrl(s3Service.uploadImage(request.getImage()))
                .build();

        diaryRepository.save(diary);
    }

    @Transactional
    public DiaryInfoResponses getAllDiaryInfo(Long exhibitId) {

        List<Diary> diaries = diaryRepository.findAllByExhibitId(exhibitId);

        return DiaryInfoResponses.builder()
                .diaryGetResponses(diaries
                        .stream()
                        .map(DiaryInfoResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public DiaryInfoResponse getDiaryInfo(Long diaryId, Long exhibitId) {

        Diary diary = diaryRepository.findByIdAndExhibitId(diaryId, exhibitId)
                .orElseThrow(DiaryNotFoundException::new);

        return DiaryInfoResponse.from(diary);
    }

    public void delete(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }
}
