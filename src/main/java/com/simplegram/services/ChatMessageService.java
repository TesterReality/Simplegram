package com.simplegram.services;

import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.MessageStatus;
import com.simplegram.entity.User;
import com.simplegram.entity.enums.ChatMessageType;
import com.simplegram.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMemberService chatMemberService;
    private final MessageStatusService messageStatusService;

    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);

        List<User> allUsersInRoom = chatMemberService.getAllUserByRoomID(chatMessage.getChatRoom().getId());
        for (User user : allUsersInRoom) {
            MessageStatus userMessageStatus = new MessageStatus();
            userMessageStatus.setMessage(chatMessage);
            userMessageStatus.setUser(user);
            userMessageStatus.setStatus(ChatMessageType.UNREAD.toString());
            messageStatusService.createMessageStatus(userMessageStatus);
        }
    }

    public ChatMessage getLastMessageByRoomId(String roomId) {
        return chatMessageRepository.findFirstByChatRoomIdOrderByDateDesc(roomId);
    }
}
