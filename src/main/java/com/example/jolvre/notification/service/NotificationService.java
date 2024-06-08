package com.example.jolvre.notification.service;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.notification.entity.NotificationMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    public void commentNotificationCreate(Long fromUserId,Long toUserId, String message) {
        if(Objects.equals(fromUserId, toUserId)){
            return;
        }

        NotificationMessage notificationMessage = new NotificationMessage(fromUserId,toUserId, message);
        log.info("알림 전송. userId : {}, message : {}",toUserId, message);
        kafkaTemplate.send("test", notificationMessage);
    }
}
