package com.example.jolvre.service.exhibition;

import com.example.jolvre.domain.exhibition.ExhibitPicture;

public interface ExhibitPictureService {
    public ExhibitPicture upload(ExhibitPicture exhibitPicture);

    public ExhibitPicture update(ExhibitPicture exhibitPicture);

    public void delete(ExhibitPicture exhibitPicture);
}
