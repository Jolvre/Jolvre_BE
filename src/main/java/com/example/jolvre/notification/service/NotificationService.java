package com.example.jolvre.notification.service;

import com.example.jolvre.notification.entity.NotificationMessage;
import com.example.jolvre.notification.entity.NotificationType;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    public void commentNotificationCreate(Long fromUserId, Long receiverId, String message, NotificationType type) {
        if (Objects.equals(fromUserId, receiverId)) {
            return;
        }

        NotificationMessage notificationMessage = NotificationMessage.builder()
                .fromUserId(fromUserId)
                .receiverId(receiverId)
                .message(message)
                .notificationType(type)
                .build();

        log.info("알림 전송. userId : {}, message : {}", receiverId, message);
        kafkaTemplate.send("test", notificationMessage);
    }
}
