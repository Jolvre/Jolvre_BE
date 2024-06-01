package com.example.jolvre.chat.repository;

import com.example.jolvre.chat.entity.ChatRoom;
import com.example.jolvre.chat.entity.ChatRoomMember;
import com.example.jolvre.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, String> {
    public ChatRoomMember findByMember(User member);

    List<ChatRoomMember> findAllByMember(User user);

    @Query(value = "select * from chat_room_member where room_id = :room_id and member != :member",
            nativeQuery = true)
    ChatRoomMember findByRoomIdAndNotSender(@Param(value = "room_id") String room_id, @Param(value = "member") Long member);

    @Query(value = "select room_id from chat_room_member where member in (:senderId, :receiverId) group by room_id having count(distinct member) = 2",
            nativeQuery = true
    )
    List<String> findRoomBySenderAndReceiver(@Param(value = "senderId") Long senderId, @Param(value = "receiverId") Long receiverId);

}
