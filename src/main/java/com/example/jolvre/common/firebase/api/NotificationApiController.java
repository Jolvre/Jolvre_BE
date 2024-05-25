package com.example.jolvre.common.firebase.api;
import com.example.jolvre.auth.PrincipalDetails;
import com.example.jolvre.common.firebase.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationApiController {

    private final NotificationService notificationService;

    @PostMapping("/new")
    public void saveNotification(@RequestBody String token,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {
        notificationService.saveNotification(token, principalDetails.getUser());
    }
}