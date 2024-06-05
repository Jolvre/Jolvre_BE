package com.example.jolvre.chat.repository;

import com.example.jolvre.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    ChatRoom findByRoomId(String roomId);
}
