package com.example.jolvre.notification.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationMessage {
    private Long fromUserId;
    private Long toUserId;
    private String message;
    private NotificationType notificationType;

    @Builder
    public NotificationMessage(Long fromUserId, Long toUserId, String message) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
    }
}
