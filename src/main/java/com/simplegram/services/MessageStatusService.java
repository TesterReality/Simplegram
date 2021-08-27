package com.simplegram.services;

import com.simplegram.entity.MessageStatus;
import com.simplegram.entity.enums.ChatMessageType;
import com.simplegram.repository.MessageStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageStatusService {
    private final MessageStatusRepository messageStatusRepository;

    public void createMessageStatus(MessageStatus messageStatus) {
        messageStatusRepository.save(messageStatus);
    }

    public int getCountUnreadMessageByRoomId(String roomID, String userId) {
        return messageStatusRepository.findAllByMessage_ChatRoom_IdAndUser_IdAndStatusEquals(roomID, userId, "UNREAD").size();
    }

    public boolean isMessageReadUsers(String messageId, String userId) {
        return !messageStatusRepository.getMessageStatus_StatusByMessage_IdAndUser_Id(messageId, userId).getStatus().equals(ChatMessageType.UNREAD.toString());
    }
}
