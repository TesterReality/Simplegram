package com.simplegram.repository;

import com.simplegram.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    @Query("select b.message from ChatMessage b where b.date = (select max(b.date) from ChatMessage b where b.chatId = :roomId)")
    String getLastMessage(@Param("roomId") String roomId);

    @Query("select max(b.date) from ChatMessage b where b.chatId = :roomId")
    LocalDateTime getDateLastMessage(@Param("roomId") String roomId);


}
