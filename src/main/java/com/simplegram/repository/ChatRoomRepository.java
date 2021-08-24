package com.simplegram.repository;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID>, JpaSpecificationExecutor<ChatRoom> {
    Boolean existsById(String uuid);
    ChatRoom getChatRoomById(String roomId);
}
