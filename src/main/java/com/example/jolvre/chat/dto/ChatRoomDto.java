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
    public static class CreateRoomResponse {
        private String roomId;
    }
}
