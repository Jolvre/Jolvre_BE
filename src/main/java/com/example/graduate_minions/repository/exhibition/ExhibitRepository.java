package com.example.graduate_minions.repository.exhibition;

import com.example.graduate_minions.domain.exhibition.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {
}
