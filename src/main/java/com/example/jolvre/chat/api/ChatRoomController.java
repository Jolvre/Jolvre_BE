package com.example.jolvre.chat.api;


import com.example.jolvre.chat.dto.ChatRoomDto;
import com.example.jolvre.chat.dto.ChatRoomDto.MessageFetchRequest;

import com.example.jolvre.auth.PrincipalDetails;

import com.example.jolvre.chat.dto.ChatRoomDto.CreateRoomRequest;
import com.example.jolvre.chat.dto.ChatRoomDto.CreateRoomResponse;
import com.example.jolvre.chat.entity.ChatMessage;
import com.example.jolvre.chat.entity.ChatRoom;
import com.example.jolvre.chat.entity.ChatRoomMember;
import com.example.jolvre.chat.repository.ChatRoomMemberRepository;
import com.example.jolvre.chat.repository.ChatRoomRepository;
import com.example.jolvre.chat.service.ChatService;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import com.example.jolvre.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final UserService userService;

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public CreateRoomResponse createRoom(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @RequestBody CreateRoomRequest createRoomRequest) {
        // sender 식별
        User sender = principalDetails.getUser();
        System.out.println(createRoomRequest.getReceiverEmail());
        // receiver 식별
        User receiver = userRepository.findByEmail(createRoomRequest.getReceiverEmail()).get();

        // 상대방과 내가 포함되어 있는 채팅방이 존재하면
        List<ChatRoomMember> chatRoom;
        chatRoom = chatService.findRoomBySenderAndReceiver(sender, receiver);
        String roomId;
        if (!chatRoom.isEmpty()) {
            ChatRoomMember room = chatRoom.get(0);
            roomId = room.getChatRoom().getRoomId();
        }

        // 상대방과 내가 포함되어 있는 채팅방이 존재하지 않으면
        else {
            // 채팅방 만들기
            ChatRoom newChatRoom = chatService.createRoom();
            roomId = newChatRoom.getRoomId();
            // 채팅방 사용자 등록
            chatService.joinRoom(sender, receiver, newChatRoom.getRoomId());
        }

        CreateRoomResponse createRoomResponse = CreateRoomResponse
                .builder()
                .roomId(roomId)
                .build();
        return createRoomResponse;
    }

    // 채팅방 나가기

    // 유저가 속해있는 채팅방 불러오기
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> findRoomByUserId(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        List<ChatRoomMember> chatRoomMembers = chatService.findRoomByUserId(user);
        ArrayList<ChatRoom> list = new ArrayList<>();
        for (ChatRoomMember chatRoom : chatRoomMembers) {
            list.add(chatRoom.getChatRoom());
        }
        return list;
    }

    @PostMapping("/room/message")
    @ResponseBody
    public List<ChatMessage> fetchChatRoom(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                           @RequestBody ChatRoomDto.MessageFetchRequest chatMessageRequest) {
        return chatService.fetchChatRoom(chatMessageRequest.getRoomId());

    }
}
