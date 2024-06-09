package com.example.jolvre.notification.dto;


import com.example.jolvre.notification.entity.NotificationType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class NotificationDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class NotificationInfoResponse {
        private String message;
        private NotificationType notificationType;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class NotificationInfoResponses {
        List<NotificationInfoResponse> notificationInfoResponses;
    }


}
