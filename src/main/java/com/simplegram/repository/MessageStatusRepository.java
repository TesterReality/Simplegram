package com.simplegram.repository;

import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, UUID> {

    @Query("select count(*) from MessageStatus m where m.message=( select b from ChatMessage b where b.id = :roomId) AND m.status = 'UNREAD' AND  m.user.id=:userId")
    int getCountUnreadMessageByRoomId(@Param("roomId") String uuid,@Param("userId") String userId);
}
