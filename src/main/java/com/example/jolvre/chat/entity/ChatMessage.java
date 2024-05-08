package com.example.jolvre.chat.entity;

import com.example.jolvre.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ChatMessage")
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private ChatRoom roomId;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "sendTime", nullable = false)
    private LocalDateTime sendTime;
}