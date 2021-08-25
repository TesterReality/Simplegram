package com.simplegram.repository;

import com.simplegram.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, UUID>, JpaSpecificationExecutor<MessageStatus> {
    List<MessageStatus> findAllByMessage_ChatRoom_IdAndUser_IdAndStatusEquals(String roomId, String userId, String status);
    MessageStatus getMessageStatus_StatusByMessage_IdAndUser_Id(String messageId,String userId);
}
