package com.example.jolvre.exhibition.repository;

import com.example.jolvre.exhibition.entity.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByExhibitId(Long id);
}
