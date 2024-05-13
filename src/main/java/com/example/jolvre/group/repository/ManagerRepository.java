package com.example.jolvre.group.repository;

import com.example.jolvre.group.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
