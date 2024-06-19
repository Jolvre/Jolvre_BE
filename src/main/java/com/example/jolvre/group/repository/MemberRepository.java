package com.example.jolvre.group.repository;

import com.example.jolvre.group.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByUserId(Long userId);

    Optional<Member> findByUserIdAndGroupExhibitId(Long userId, Long groupId);
}
