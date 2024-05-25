package com.example.jolvre.common.firebase.Repository;
import com.example.jolvre.common.firebase.Entity.Notification;
import com.example.jolvre.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jolvre.common.firebase.Entity.Notification;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByUser(User user);
}