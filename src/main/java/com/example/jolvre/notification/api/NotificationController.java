package com.example.jolvre.notification.api;

import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.notification.dto.NotificationDTO.NotificationInfoResponses;
import com.example.jolvre.notification.entity.NotificationType;
import com.example.jolvre.notification.service.EmitterService;
import com.example.jolvre.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "알림", description = "알림 설정 및 정보를 관리합니다")
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

    @Operation(summary = "sse 연결", description = "서버와 sse 커넥션을 설정합니다")
    @GetMapping(produces = "text/event-stream")
    public ResponseEntity<SseEmitter> stream(@AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        log.info("emitter connect");

        SseEmitter response = emitterService.addEmitter(String.valueOf(principalDetails.getId()), lastEventId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "알림 조회", description = "자신의 알림을 조회합니닥")
    @GetMapping("/me")
    public ResponseEntity<NotificationInfoResponses> getNotificationInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        NotificationInfoResponses response = notificationService.getNotificationInfoById(
                principalDetails.getId());

        return ResponseEntity.ok(response);
    }
}
