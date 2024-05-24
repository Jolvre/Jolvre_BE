package com.example.jolvre.chat.api;


import com.example.jolvre.chat.dto.ChatRoomDto;
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
    public void message(ChatRoomDto.ChatMessageRequest message, @DestinationVariable("roomId") String roomId){
        System.out.println("[ChatController] message");

        // 메세지 송신자 식별
        User sender = userRepository.findByNickname(message.getSender()).get();

        // 메세지 엔티티 형태로 저장
        ChatMessage chatMessage = new ChatMessage();
        chatMessage = ChatMessage.builder()
                        .message(message.getMessage())
                        .sendTime(LocalDateTime.now())
                        .sender(sender)
                        .roomId(chatRoomRepository.findByRoomId(roomId)).build();
        chatMessageRepository.save(chatMessage);

        // 상대방에게 전해줄 메세지 형태로 저장
        ChatRoomDto.ChatMessageResponse chatMessageResponse = new ChatRoomDto.ChatMessageResponse();
        chatMessageResponse.setMessage(message.getMessage());
        chatMessageResponse.setNickname(message.getSender());
        chatMessageResponse.setSendTime(LocalDateTime.now());

        // /sub/chat/{roomId}로 메세지 보내기
        messagingTemplate.convertAndSend("/sub/chat/" + roomId, chatMessageResponse);
    }
}

