package com.example.jolvre.repository.exhibition;

import com.example.jolvre.domain.exhibition.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {
}
