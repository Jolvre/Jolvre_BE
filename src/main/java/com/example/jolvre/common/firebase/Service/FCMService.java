package com.example.jolvre.common.firebase.Service;
import com.example.jolvre.common.error.user.UserNotFoundException;
import com.example.jolvre.common.firebase.DTO.FCMMessage;
import com.example.jolvre.common.firebase.Entity.FCMNotification;
import com.example.jolvre.common.firebase.Repository.FCMNotificationRepository;
import com.example.jolvre.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {

    private final FCMNotificationRepository fcmNotificationRepository;
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/jolvre-cdfe2/messages:send";
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveNotification(String token, User user) {
        FCMNotification fcmNotification = FCMNotification.builder()
                .token(token)
                .user(user)
                .build();

        log.info("notification 엔티티 생성 완료");

        //fcmNotification.confirmUser(user);
        fcmNotificationRepository.save(fcmNotification);
    }

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        log.info(response.body().string());
    }

    public String getTargetToken(User user) {
        FCMNotification FCMNotification = fcmNotificationRepository.findByUser(user)
                .orElseThrow(UserNotFoundException::new);
        return FCMNotification.getToken();
    }

    public String getAccessToken() throws IOException {
        String firebaseConfigPath = "jolvre-cdfe2-418a2fdc3ed8.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FCMMessage fcmmessage = FCMMessage.builder()
                .message(FCMMessage.Message.builder()
                        .token(targetToken)
                        .notification(FCMMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();

        return objectMapper.writeValueAsString(fcmmessage);
    }
}