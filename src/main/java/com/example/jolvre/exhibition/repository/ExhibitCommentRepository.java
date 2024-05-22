package com.example.jolvre.exhibition.repository;

import com.example.jolvre.exhibition.entity.ExhibitComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitCommentRepository extends JpaRepository<ExhibitComment, Long> {

    Optional<ExhibitComment> findByExhibitId(Long exhibitId);

    Optional<ExhibitComment> findByUserId(Long userId);

    Optional<ExhibitComment> findByUserIdAndExhibitId(Long userId, Long exhibitId);

    Optional<ExhibitComment> findByIdAndUserId(Long commentId, Long userId);

    List<ExhibitComment> findAllByExhibitId(Long exhibitId);

}
