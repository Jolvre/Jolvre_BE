package com.example.jolvre.post.repository;

import com.example.jolvre.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserId(Long userId, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}
