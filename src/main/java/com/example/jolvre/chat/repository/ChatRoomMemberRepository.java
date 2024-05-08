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
    @Query(value = "select c1.id as id, c1.room_id as room_id, c1.member as member " +
            "from chat_room_member as c1, chat_room_member as c2 " +
            "where c1.room_id = c2.room_id and c1.member = :senderId and c2.member = :receiverId",
            nativeQuery = true
    )
    List<ChatRoomMember> findRoomBySenderAndReceiver(@Param(value = "senderId") Long senderId, @Param(value = "receiverId") Long receiverId);

}
