package com.example.jolvre.chat.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jolvre.auth.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtService jwtService;
    @Value("${jwt.secretKey}")
    private String secretKey;
    private static final String BEARER = "Bearer ";
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("[preSend]");
        // Auth 인증 절차
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);


        if (headerAccessor.getCommand() == StompCommand.CONNECT){
            System.out.println("연결 시작");

            String authorizationHeader = headerAccessor.getNativeHeader("Authorization").toString();
            String accessToken = authorizationHeader.substring(7, authorizationHeader.length() - 1);
            System.out.println(accessToken);

            // 채팅방에 입장할 경우

            // 이전에 안 읽었던 채팅 다 읽음 처리 로직

        } else if (headerAccessor.getCommand() == StompCommand.DISCONNECT) {
            System.out.println("여긴 연결 끊어진거유");
        }
        else {
            System.out.println("이건 메세지여");
        }
        return message;
    }
}
