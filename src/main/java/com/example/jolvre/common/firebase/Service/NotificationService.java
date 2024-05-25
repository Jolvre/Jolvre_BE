package com.example.jolvre.common.firebase.Service;
import com.example.jolvre.common.error.user.UserNotFoundException;
import com.example.jolvre.common.firebase.DTO.NotificationRequestDto;
import com.example.jolvre.common.firebase.Entity.Notification;
import com.example.jolvre.common.firebase.Repository.NotificationRepository;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveNotification(String token, User user) {
        Notification notification = Notification.builder()
                .token(token)
                .build();

        notification.confirmUser(user);
        notificationRepository.save(notification);
    }

    public void sendNotification(NotificationRequestDto request)
            throws ExecutionException, InterruptedException {
        Message message = Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(WebpushNotification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getMsg())
                                .build())
                        .build())
                .setToken(request.getToken())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        log.info("Send message : " + response);
    }

    public String getNotificationToken(User user) {
        Notification notification = notificationRepository.findByUser(user)
                .orElseThrow(UserNotFoundException::new);
        return notification.getToken();
    }
}