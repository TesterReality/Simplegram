package com.simplegram.repository;

import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, UUID> {

}
