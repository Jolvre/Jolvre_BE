package com.example.jolvre.chat.api;


import com.example.jolvre.chat.dto.ChatRoomDto;

import com.example.jolvre.auth.PrincipalDetails;

import com.example.jolvre.chat.dto.ChatRoomDto.CreateRoomRequest;
import com.example.jolvre.chat.dto.ChatRoomDto.CreateRoomResponse;
import com.example.jolvre.chat.dto.ChatRoomDto.FetchChatRoomResponse;
import com.example.jolvre.chat.entity.ChatRoom;
import com.example.jolvre.chat.dto.ChatRoomDto.ChatMessageResponse;
import com.example.jolvre.chat.entity.ChatRoomMember;
import com.example.jolvre.chat.repository.ChatRoomMemberRepository;
import com.example.jolvre.chat.repository.ChatRoomRepository;
import com.example.jolvre.chat.service.ChatService;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import com.example.jolvre.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        User sender = principalDetails.getUser();
        User receiver = userRepository.findByNickname(createRoomRequest.getReceiverNickname()).get();


        List<String> chatRoomIds = chatService.findRoomBySenderAndReceiver(sender, receiver);
        String roomId;
        if (!chatRoomIds.isEmpty()) {
            System.out.println("Not Empty");
            roomId = chatRoomIds.get(0);
        }

        else {
            System.out.println("Empty");
            ChatRoom newChatRoom = chatService.createRoom();
            roomId = newChatRoom.getRoomId();

            chatService.joinRoom(sender, receiver, newChatRoom.getRoomId());
        }

        CreateRoomResponse createRoomResponse = CreateRoomResponse
                .builder()
                .roomId(roomId)
                .build();
        return createRoomResponse;
    }

    // 유저가 속해있는 채팅방 불러오기
    @GetMapping("/rooms")
    @ResponseBody
    public Set<FetchChatRoomResponse> findRoomByUserId(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        List<ChatRoomMember> chatRoomMembers = chatService.findRoomByUserId(user);
        Set<String> roomIdList = new HashSet<>();

        for (ChatRoomMember chatRoomMember: chatRoomMembers) {
            roomIdList.add(chatRoomMember.getChatRoom().getRoomId());
        }

        Set<FetchChatRoomResponse> ChatRoomlist = new HashSet<>();
        for (String roomId : roomIdList) {
            ChatRoomMember chatRoom = chatService.findRoomByRoomIdAndNotSender(roomId, principalDetails.getUser().getId());

            User receiver = chatRoom.getMember();
            String receiverNickname = receiver.getNickname();
            String receiverProfileImg = receiver.getImageUrl();

            ChatMessageResponse chatMessageResponse = chatService.getLastMsg(roomId).get(0);
            String lastMsgContent = chatMessageResponse.getMessage();
            LocalDateTime lastMsgDate = chatMessageResponse.getSendTime();

            FetchChatRoomResponse fetchChatRoomResponse = FetchChatRoomResponse.builder()
                    .roomId(roomId)
                    .receiverNickname(receiverNickname)
                    .receiverProfileImg(receiverProfileImg)
                    .lastMsgContent(lastMsgContent)
                    .lastMsgDate(lastMsgDate)
                    .build();

            ChatRoomlist.add(fetchChatRoomResponse);
        }

        return ChatRoomlist;
    }

    @PostMapping("/room/message")
    @ResponseBody
    public List<ChatMessageResponse> fetchChatRoom(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                               @RequestBody ChatRoomDto.MessageFetchRequest chatMessageRequest) {
        return chatService.fetchChatRoom(chatMessageRequest.getRoomId());

    }
}
