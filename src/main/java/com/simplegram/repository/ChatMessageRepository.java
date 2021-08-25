package com.simplegram.repository;

import com.simplegram.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID>, JpaSpecificationExecutor<ChatMessage> {
    ChatMessage findFirstByChatRoomIdOrderByDateDesc(String chatId);
}
