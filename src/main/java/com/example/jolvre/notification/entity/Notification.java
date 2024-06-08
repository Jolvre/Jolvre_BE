package com.example.jolvre.notification.entity;

import com.example.jolvre.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private Long receiverId;
    
    @Builder
    public Notification(String message, NotificationType notificationType, Long receiverId) {
        this.message = message;
        this.notificationType = notificationType;
        this.receiverId = receiverId;
    }
}
