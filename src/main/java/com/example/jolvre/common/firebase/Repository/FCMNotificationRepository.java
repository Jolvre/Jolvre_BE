package com.example.jolvre.common.firebase.Repository;
import com.example.jolvre.common.firebase.Entity.FCMNotification;
import com.example.jolvre.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FCMNotificationRepository extends JpaRepository<FCMNotification, Long> {
    Optional<FCMNotification> findByUser(User user);
    void deleteByUser(User user);
}