package com.simplegram.repository;

import com.simplegram.entity.ChatMessageAttachments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageAttachmentsRepository extends JpaRepository<ChatMessageAttachments, UUID> {

    List<ChatMessageAttachments> findAllByMessage_Id(String roomId);
}
