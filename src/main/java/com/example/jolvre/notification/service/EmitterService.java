package com.example.jolvre.notification.service;

import com.example.jolvre.notification.entity.Notification;
import com.example.jolvre.notification.entity.NotificationMessage;
import com.example.jolvre.notification.repository.EmitterRepository;
import com.example.jolvre.notification.repository.NotificationRepository;
import com.example.jolvre.user.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmitterService {
    //    private final NotificationsService notificationsService;
    private final EmitterRepository emitterRepository;
    private final UserService userService;
    private final NotificationRepository notificationRepository;

    private static final int MAX_NOTIFICATIONS_COUNT = 6;
    private static final Long TIME_OUT = 60L * 1000;

    @KafkaListener(topics = "test", groupId = "jolvre")
    public void listen(NotificationMessage message) {
        String userId = String.valueOf(message.getToUserId());

        Notification notification = Notification.builder()
                .message(message.getMessage())
                .receiver(userService.getUserById(message.getToUserId()))
                .notificationType(message.getNotificationType())
                .build();

        notificationRepository.save(notification);

        List<Notification> notifications = notificationRepository.findAllByReceiverId(message.getToUserId());
        if (notifications.size() > 6) {
            notifications.sort((o1, o2) -> {
                // 두 번째 인자가 크다면 양수, 첫 번째 인자가 크다면 음수
                return o2.getCreatedDate().compareTo(o1.getCreatedDate());
            });

            notificationRepository.deleteAll(notifications);
            notificationRepository.saveAll(notifications.subList(0, 6));
        }

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithById(userId);

        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, message);
                    sendToClient(emitter, key, message);
                }
        );
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data)
                    .reconnectTime(0)
            );

            log.info("Kafka로 부터 전달 받은 메세지 전송. emitterId : {}, message : {}", emitterId, data);
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
            log.error("메시지 전송 에러 : {}", e);
        }
    }

    public SseEmitter addEmitter(String userId, String lastEventId) {

        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(TIME_OUT));
        log.info("emitterId : {} 사용자 emitter 연결 ", emitterId);

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            emitterRepository.deleteById(emitterId);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitterRepository.deleteById(emitterId);
        });

        sendToClient(emitter, emitterId, "connected!"); // 503 에러방지 더미 데이터

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithById(userId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }
}
