package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.repository.DiaryImageRepository;
import com.example.jolvre.exhibition.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryImageRepository diaryImageRepository;
}
