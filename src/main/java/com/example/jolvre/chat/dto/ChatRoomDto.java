package com.example.jolvre.chat.dto;

import com.example.jolvre.chat.entity.ChatMessage;
import com.example.jolvre.chat.entity.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRoomRequest {
        private String receiverNickname;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MessageFetchRequest {
        private String roomId;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ChatMessageRequest {
        private String message;
        private String sender;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ChatMessageResponse {
        private String message;
        private LocalDateTime sendTime;
        private String nickname;
        private String receiverProfileImg;
    }

    public List<ChatMessageResponse> convertToChatMessageResponse(List<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .map(chatMessage -> new ChatMessageResponse(
                        chatMessage.getMessage(),
                        chatMessage.getSendTime(),
                        chatMessage.getSender().getNickname(),
                        chatMessage.getSender().getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRoomResponse {
        private String roomId;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class FetchChatRoomResponse {
        private String roomId;
        private String receiverNickname;
        private String receiverProfileImg;
        private String lastMsgContent;
        private LocalDateTime lastMsgDate;
    }
}
