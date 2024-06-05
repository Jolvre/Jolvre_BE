package com.example.jolvre.exhibition.repository;

import com.example.jolvre.exhibition.entity.ExhibitImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitImageRepository extends JpaRepository<ExhibitImage, Long> {
    List<ExhibitImage> findAllByExhibitId(Long id);

    void deleteAllByExhibitId(Long exhibitId);
}
