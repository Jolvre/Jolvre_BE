package com.example.graduate_minions.repository.post;

import com.example.graduate_minions.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
