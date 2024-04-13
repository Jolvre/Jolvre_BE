package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.entity.ExhibitPicture;
import com.example.jolvre.exhibition.repository.ExhibitPictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExhibitPictureService {
    private final ExhibitPictureRepository exhibitPictureRepository;

    public ExhibitPicture upload(ExhibitPicture exhibitPicture) {
        return exhibitPictureRepository.save(exhibitPicture);
    }

    public ExhibitPicture update(ExhibitPicture exhibitPicture) {
        return null;
    }

    public void delete(ExhibitPicture exhibitPicture) {
        exhibitPictureRepository.delete(exhibitPicture);
    }
}
