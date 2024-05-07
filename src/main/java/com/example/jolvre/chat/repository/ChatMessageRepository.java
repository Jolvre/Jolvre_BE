package com.example.jolvre.chat.repository;

import com.example.jolvre.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long>{

}
