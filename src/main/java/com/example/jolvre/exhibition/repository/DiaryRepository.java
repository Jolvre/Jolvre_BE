package com.example.jolvre.exhibition.repository;

import com.example.jolvre.exhibition.entity.Diary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByExhibitId(Long exhibitId);

    Optional<Diary> findByIdAndExhibitId(Long id, Long exhibitId);


}

