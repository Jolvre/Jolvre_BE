package com.example.graduate_minions.repository.post;

import com.example.graduate_minions.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
