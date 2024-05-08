package com.example.jolvre.chat.repository;

import com.example.jolvre.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long>{

    @Query(value = "select * from chat_message where room_id = :room_id limit :defaultLoad",
    nativeQuery = true)
    public List<ChatMessage> findByRoomId(String room_id, int defaultLoad);

}
