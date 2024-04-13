package com.example.jolvre.exhibition.service.config;

import com.example.jolvre.exhibition.repository.DiaryPictureRepository;
import com.example.jolvre.exhibition.repository.DiaryRepository;
import com.example.jolvre.exhibition.repository.ExhibitPictureRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.exhibition.service.DiaryPictureService;
import com.example.jolvre.exhibition.service.DiaryService;
import com.example.jolvre.exhibition.service.ExhibitPictureService;
import com.example.jolvre.exhibition.service.ExhibitService;
import com.example.jolvre.exhibition.service.impl.DiaryPictureServiceImpl;
import com.example.jolvre.exhibition.service.impl.DiaryServiceImpl;
import com.example.jolvre.exhibition.service.impl.ExhibitPictureServiceImpl;
import com.example.jolvre.exhibition.service.impl.ExhibitServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExhibitionConfig {
    private final ExhibitRepository exhibitRepository;
    private final ExhibitPictureRepository exhibitPictureRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryPictureRepository diaryPictureRepository;

    @Bean
    public DiaryService diaryService() {
        return new DiaryServiceImpl(diaryRepository);
    }

    @Bean
    public DiaryPictureService diaryPictureService() {
        return new DiaryPictureServiceImpl(diaryPictureRepository);
    }

    @Bean
    public ExhibitService exhibitService() {
        return new ExhibitServiceImpl(exhibitRepository);
    }

    @Bean
    public ExhibitPictureService exhibitPictureService() {
        return new ExhibitPictureServiceImpl(exhibitPictureRepository);
    }
}
