package com.example.jolvre.chat.entity;

import com.example.jolvre.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Entity
@Table(name = "ChatRoom")
@DynamicUpdate
@Builder
@Setter
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    private String roomId;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ChatRoom(String roomId, LocalDateTime createdAt){
        this.roomId = roomId;
        this.createdAt = createdAt;
    }
}
