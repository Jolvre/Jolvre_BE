package com.example.jolvre.common.firebase.api;
import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.common.firebase.Repository.FCMNotificationRepository;
import com.example.jolvre.common.firebase.Service.FCMService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Firebase Cloud Message", description = "FCM 푸쉬알람 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class FCMController {

    private final FCMService FCMService;
    private final FCMNotificationRepository fcmNotificationRepository;

    @PostMapping("/new")
    @Transactional
    public void saveNotification(@RequestBody String token,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (fcmNotificationRepository.findByUser(principalDetails.getUser()).isEmpty()) {
            FCMService.saveNotification(token, principalDetails.getUser());
        } else {
            fcmNotificationRepository.deleteByUser(principalDetails.getUser());
            FCMService.saveNotification(token, principalDetails.getUser());
        }
    }
}