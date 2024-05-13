package com.example.jolvre.chat.api;


import com.example.jolvre.auth.PrincipalDetails;

import com.example.jolvre.chat.entity.ChatMessage;
import com.example.jolvre.chat.entity.ChatRoomMember;
import com.example.jolvre.chat.repository.ChatMessageRepository;
import com.example.jolvre.chat.repository.ChatRoomMemberRepository;
import com.example.jolvre.chat.repository.ChatRoomRepository;
import com.example.jolvre.user.entity.User;

import com.example.jolvre.user.repository.UserRepository;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserRepository userRepository;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/sub/chat/{roomId}")

    public void message(ChatMessageRequest message, @DestinationVariable("roomId") String roomId){
        User sender = userRepository.findByEmail(message.getSender()).get();
        ChatMessage chatMessage = ChatMessage.builder()
                        .message(message.getMessage())
                        .sendTime(LocalDateTime.now())
                        .sender(sender)
                        .roomId(chatRoomRepository.findByRoomId(roomId))
                        .build();
        System.out.println(chatMessage);

        chatMessageRepository.save(chatMessage);

        messagingTemplate.convertAndSend("/sub/chat/" + roomId, chatMessage);
        // 상대방에게 알림 보내기

//        ChatRoomMember chatRoomMembers = chatRoomMemberRepository.findByRoomIdAndNotSender(roomId, 17L);
//        User receiver = chatRoomMembers.getMember();
//        System.out.println(receiver.getId());
//        messagingTemplate.convertAndSend("/sub/chat/"+receiver.getId(), chatMessage);

    }
}
