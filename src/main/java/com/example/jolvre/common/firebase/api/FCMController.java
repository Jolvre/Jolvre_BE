//package com.example.jolvre.common.firebase.api;
//
//import com.example.jolvre.auth.PrincipalDetails;
//import com.example.jolvre.common.firebase.Repository.UserFcmTokenRepository;
//import com.example.jolvre.common.firebase.Service.FCMService;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name = "Firebase Cloud Message", description = "FCM 푸쉬알람 API")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/notification")
//public class FCMController {
//
//    private final FCMService fcmService;
//    private final UserFcmTokenRepository userFcmTokenRepository;
//
//    @PostMapping("/new")
//    public void saveNotification(@RequestBody String token,
//                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        //유저의 토큰이 이미 존재하는 경우
//        if (userFcmTokenRepository.existsByUser(principalDetails.getUser())) {
//            Long TokenId = userFcmTokenRepository.findByUser(principalDetails.getUser()).get().getUserFcmTokenId();
//            userFcmTokenRepository.deleteById(TokenId);
//        }
//        fcmService.saveUerFcmToken(token, principalDetails.getUser());
//    }
//}