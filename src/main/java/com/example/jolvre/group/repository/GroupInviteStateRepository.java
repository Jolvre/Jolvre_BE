package com.example.jolvre.group.repository;

import com.example.jolvre.group.entity.GroupInviteState;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupInviteStateRepository extends JpaRepository<GroupInviteState, Long> {

    List<GroupInviteState> findAllByUserId(Long userId);

    Optional<GroupInviteState> findByIdAndUserId(Long Id, Long userId);

    boolean existsByGroupExhibitIdAndUserId(Long groupId, Long userId);
}
