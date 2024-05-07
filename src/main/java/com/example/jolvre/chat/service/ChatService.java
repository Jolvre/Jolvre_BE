package com.example.jolvre.chat.service;

import com.example.jolvre.chat.entity.ChatRoom;
import com.example.jolvre.chat.entity.ChatRoomMember;
import com.example.jolvre.chat.repository.ChatRoomMemberRepository;
import com.example.jolvre.chat.repository.ChatRoomRepository;
import com.example.jolvre.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final EntityManager entityManager;
    private void init(){
        Map<String, ChatRoom> chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(){
        return chatRoomRepository.findAll();
    }

    public ChatRoom findByRoomId(String roomId){
        return chatRoomRepository.findByRoomId(roomId);
    }
    public List<ChatRoomMember> findRoomByUserId(User user){ return chatRoomMemberRepository.findAllByMember(user);}
    public List<ChatRoomMember> findRoomBySenderAndReceiver(User sender, User receiver){
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();
        return chatRoomMemberRepository.findRoomBySenderAndReceiver(senderId, receiverId);
    }
    public ChatRoom createRoom(){
        String roomId = UUID.randomUUID().toString(); // 랜덤한 아이디 생성해서

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .createdAt(LocalDateTime.now())
                .build();

        return chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void joinRoom(User sender, User receiver, String roomId){

        ChatRoom chatRoom1 = chatRoomRepository.findByRoomId(roomId);
        ChatRoomMember chatRoomMemberSender = ChatRoomMember.builder()
                .chatRoom(chatRoom1)
                .member(sender)
                .build();
        System.out.println("sender!");
        System.out.println(chatRoomMemberSender.getMember().getId());
        chatRoomMemberRepository.save(chatRoomMemberSender);
        ChatRoom chatRoom2 = chatRoomRepository.findByRoomId(roomId);
        ChatRoomMember chatRoomMemberReceiver = entityManager.merge(
                ChatRoomMember.builder()
                .chatRoom(chatRoom2)
                .member(receiver)
                .build());
        System.out.println("receiver!");
        System.out.println(chatRoomMemberReceiver.getMember().getId());
        System.out.println("chatRoomMemberReceiver");

        chatRoomMemberRepository.save(chatRoomMemberReceiver);
    }

}
