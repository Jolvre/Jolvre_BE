package com.example.jolvre.notification.repository;

import com.example.jolvre.notification.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Long countByReceiverId(Long receiverId);

    List<Notification> findAllByReceiverId(Long receiverId);

}
