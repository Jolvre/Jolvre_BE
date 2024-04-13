package com.example.jolvre.exhibition.service.impl;

import com.example.jolvre.exhibition.entity.ExhibitPicture;
import com.example.jolvre.exhibition.repository.ExhibitPictureRepository;
import com.example.jolvre.exhibition.service.ExhibitPictureService;
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
