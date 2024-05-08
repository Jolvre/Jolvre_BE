package com.example.jolvre.chat.api;

import com.example.jolvre.auth.entity.PrincipalDetails;
import com.example.jolvre.chat.entity.ChatMessage;
import com.example.jolvre.chat.entity.ChatRoom;
import com.example.jolvre.chat.entity.ChatRoomMember;
import com.example.jolvre.chat.repository.ChatMessageRepository;
import com.example.jolvre.chat.repository.ChatRoomMemberRepository;
import com.example.jolvre.chat.repository.ChatRoomRepository;
import com.example.jolvre.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/sub/chat/{roomId}")
    public void message(@AuthenticationPrincipal PrincipalDetails principalDetails, ChatMessage message, @DestinationVariable("roomId") String roomId){
        log.info("chat {} send by {} to room number{}", message.getMessage(), message.getMessageId(), roomId);
        System.out.println(chatRoomRepository.findByRoomId(roomId).getRoomId());
        System.out.println(LocalDateTime.now());
        ChatMessage chatMessage = ChatMessage.builder()
                        .message(message.getMessage())
                        .sender(principalDetails.getUser())
                        .sendTime(LocalDateTime.now())
                        .roomId(chatRoomRepository.findByRoomId(roomId))
                        .build();

        chatMessageRepository.save(chatMessage);

        messagingTemplate.convertAndSend("/sub/chat/"+roomId, chatMessage);
        // 상대방에게 알림 보내기
        ChatRoomMember chatRoomMembers = chatRoomMemberRepository.findByRoomIdAndNotSender(roomId, 17L);
        User receiver = chatRoomMembers.getMember();
        System.out.println(receiver.getId());
        messagingTemplate.convertAndSend("/sub/chat/"+receiver.getId(), chatMessage);
    }
}
