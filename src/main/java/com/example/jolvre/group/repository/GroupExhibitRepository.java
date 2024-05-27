package com.example.jolvre.group.repository;

import com.example.jolvre.group.entity.GroupExhibit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupExhibitRepository extends JpaRepository<GroupExhibit, Long> {
    Page<GroupExhibit> findByNameContaining(String keyword, Pageable pageable);
}
