package com.simplegram.repository;

import com.simplegram.entity.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {

    @Query("select b.id_chat from ChatMember b where b.id_user = :uuid")
    List<String> findAllRoomIDByUserUUID(@Param("uuid") String userUUID);

    @Query("select b.id_user from ChatMember b where b.id_chat = :chatRoomID")
    List<String> findUsersIDByChatRoomID(@Param("chatRoomID") String chatRoomUUID);
}
