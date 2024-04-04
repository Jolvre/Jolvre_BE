package com.example.jolvre.service.exhibition.config;

import com.example.jolvre.repository.exhibition.DiaryPictureRepository;
import com.example.jolvre.repository.exhibition.DiaryRepository;
import com.example.jolvre.repository.exhibition.ExhibitPictureRepository;
import com.example.jolvre.repository.exhibition.ExhibitRepository;
import com.example.jolvre.service.exhibition.DiaryPictureService;
import com.example.jolvre.service.exhibition.DiaryService;
import com.example.jolvre.service.exhibition.ExhibitPictureService;
import com.example.jolvre.service.exhibition.ExhibitService;
import com.example.jolvre.service.exhibition.impl.DiaryPictureServiceImpl;
import com.example.jolvre.service.exhibition.impl.DiaryServiceImpl;
import com.example.jolvre.service.exhibition.impl.ExhibitPictureServiceImpl;
import com.example.jolvre.service.exhibition.impl.ExhibitServiceImpl;
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
