package com.example.jolvre.exhibition.repository;

import com.example.jolvre.exhibition.entity.Exhibit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {
    List<Exhibit> findAllByUserId(long userId);

    List<Exhibit> findAllByDistribute(boolean distribute);
}
