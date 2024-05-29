package com.example.jolvre.exhibition.repository;

import com.example.jolvre.exhibition.entity.Exhibit;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {
    List<Exhibit> findAllByUserId(Long userId);

    List<Exhibit> findAllByDistribute(boolean distribute);

    Optional<Exhibit> findByIdAndUserId(Long exhibitID, Long userId);

    void deleteByIdAndUserId(Long exhibitId, Long userId);

    List<Exhibit> findAllByWorkTypeAndDistribute(String workType, boolean distribute);

    Page<Exhibit> findByDistributeAndTitleContaining(boolean distribute, String keyword, Pageable pageable);

    Page<Exhibit> findAllByDistribute(boolean distribute, Pageable pageable);

}
