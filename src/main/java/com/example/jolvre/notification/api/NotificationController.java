package com.example.jolvre.notification.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.notification.entity.NotificationType;
import com.example.jolvre.notification.service.EmitterService;
import com.example.jolvre.notification.service.NotificationService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final EmitterService emitterService;

    @GetMapping("/test")
    public ResponseEntity<Void> test() {
        notificationService.commentNotificationCreate(1L, 2L, "야 !", NotificationType.EXHIBIT_COMMENT);

        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = "text/event-stream")
    public ResponseEntity<SseEmitter> stream(@AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId)
            throws IOException {
        log.info("emitter connect");

        SseEmitter response = emitterService.addEmitter(String.valueOf(principalDetails.getId()), lastEventId);

        return ResponseEntity.ok(response);
    }
}
