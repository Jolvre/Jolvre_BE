package com.example.jolvre.notification.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class NotificationMessage {
    private Long fromUserId;
    private Long receiverId;
    private String message;
    private NotificationType notificationType;

    @Builder
    public NotificationMessage(Long fromUserId, Long receiverId, String message, NotificationType notificationType) {
        this.fromUserId = fromUserId;
        this.receiverId = receiverId;
        this.message = message;
        this.notificationType = notificationType;
    }
}
