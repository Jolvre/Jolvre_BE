package com.example.jolvre.chat.dto;

import com.example.jolvre.chat.entity.ChatRoom;
import lombok.*;

public class ChatRoomDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRoomRequest {
        private String receiverEmail;

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

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateRoomResponse {
        private String roomId;
    }
}
