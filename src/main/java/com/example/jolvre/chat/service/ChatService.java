package com.example.jolvre.chat.service;

import com.example.jolvre.chat.dto.ChatRoomDto;
import com.example.jolvre.chat.dto.ChatRoomDto.ChatMessageResponse;
import com.example.jolvre.chat.entity.ChatMessage;
import com.example.jolvre.chat.entity.ChatRoom;
import com.example.jolvre.chat.entity.ChatRoomMember;
import com.example.jolvre.chat.repository.ChatMessageRepository;
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
    private final ChatMessageRepository chatMessageRepository;
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

    public ChatRoomMember findRoomByRoomIdAndNotSender(String roomId, Long senderId){
        return chatRoomMemberRepository.findByRoomIdAndNotSender(roomId, senderId);
    }
    // 사용자와 상대방이 속해있는 채팅방 찾기
    public List<String> findRoomBySenderAndReceiver(User sender, User receiver){
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();
        return chatRoomMemberRepository.findRoomBySenderAndReceiver(senderId, receiverId);
    }

    // 채팅방 만들기
    public ChatRoom createRoom(){
        String roomId = UUID.randomUUID().toString(); // 랜덤한 아이디 생성해서

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .createdAt(LocalDateTime.now())
                .build();

        return chatRoomRepository.save(chatRoom);
    }

    // 채팅방 입장
    @Transactional
    public void joinRoom(User sender, User receiver, String roomId){

        ChatRoom chatRoom1 = chatRoomRepository.findByRoomId(roomId);
        ChatRoomMember chatRoomMemberSender = ChatRoomMember.builder()
                .chatRoom(chatRoom1)
                .member(sender)
                .build();

        chatRoomMemberRepository.save(chatRoomMemberSender);
        ChatRoom chatRoom2 = chatRoomRepository.findByRoomId(roomId);
        ChatRoomMember chatRoomMemberReceiver = entityManager.merge(
                ChatRoomMember.builder()
                .chatRoom(chatRoom2)
                .member(receiver)
                .build());

        chatRoomMemberRepository.save(chatRoomMemberReceiver);
    }

    // 채팅 내역 불러오기
    public List<ChatMessageResponse> fetchChatRoom(String roomId){
        int defaultLoad = 30;
        List<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(roomId, defaultLoad);

        ChatRoomDto chatRoomDto = new ChatRoomDto();
        List<ChatMessageResponse> chatMessageResponses = chatRoomDto.convertToChatMessageResponse(chatMessages);
        return chatMessageResponses;
    }

    // 마지막 채팅 내역 불러오기
    public List<ChatMessageResponse> getLastMsg(String roomId){
        List<ChatMessage> lastMsg = chatMessageRepository.findOneByRoomId(roomId,1);
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        List<ChatMessageResponse> chatMessageResponse = chatRoomDto.convertToChatMessageResponse(lastMsg);
        return chatMessageResponse;
    }

}
