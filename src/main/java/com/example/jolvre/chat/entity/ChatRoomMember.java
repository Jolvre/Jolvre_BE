package com.example.jolvre.chat.entity;

import com.example.jolvre.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "ChatRoomMember")
public class ChatRoomMember {

    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "roomId")
    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne // N:1
    @JoinColumn(name = "member")
    private User member;

    public ChatRoomMember(Long id, ChatRoom chatRoom, User user){
        this.id = id;
        this.chatRoom = chatRoom;
        this.member = user;
    }

}
