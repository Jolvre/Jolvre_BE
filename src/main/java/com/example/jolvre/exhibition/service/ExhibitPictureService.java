package com.example.jolvre.exhibition.service;

import com.example.jolvre.exhibition.entity.ExhibitPicture;

public interface ExhibitPictureService {
    public ExhibitPicture upload(ExhibitPicture exhibitPicture);

    public ExhibitPicture update(ExhibitPicture exhibitPicture);

    public void delete(ExhibitPicture exhibitPicture);
}
