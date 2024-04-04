package com.example.jolvre.service.exhibition.impl;

import com.example.jolvre.domain.exhibition.ExhibitPicture;
import com.example.jolvre.repository.exhibition.ExhibitPictureRepository;
import com.example.jolvre.service.exhibition.ExhibitPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExhibitPictureServiceImpl implements ExhibitPictureService {
    private final ExhibitPictureRepository exhibitPictureRepository;

    @Override
    public ExhibitPicture upload(ExhibitPicture exhibitPicture) {
        return exhibitPictureRepository.save(exhibitPicture);
    }

    @Override
    public ExhibitPicture update(ExhibitPicture exhibitPicture) {
        return null;
    }

    @Override
    public void delete(ExhibitPicture exhibitPicture) {
        exhibitPictureRepository.delete(exhibitPicture);
    }
}
