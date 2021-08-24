package com.simplegram.services;

import com.simplegram.entity.MessageStatus;
import com.simplegram.repository.MessageStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageStatusService {
    private final MessageStatusRepository messageStatusRepository;

    public void createMessageStatus(MessageStatus messageStatus)
    {
        messageStatusRepository.save(messageStatus);
    }

    public int getCountUnreadMessageByRoomId(String roomID, String userId)
    {
        return messageStatusRepository.findAllByMessage_ChatRoom_IdAndUser_IdAndStatusEquals(roomID,userId,"UNREAD").size();
    }
}
