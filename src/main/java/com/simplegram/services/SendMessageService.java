package com.simplegram.services;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.ChatRoom;
import com.simplegram.entity.MessageStatus;
import com.simplegram.entity.enums.ChatMessageType;
import com.simplegram.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Log4j2
@Component
public class SendMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final SaveChatMessageFileService attachSaver;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final MessageStatusRepository messageStatusRepository;

    @Transactional
    public void sendMessage(String roomId, String message, List<MultipartFile> files,String userId) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setChatRoomId(roomId);
        chatMessage.setType(ChatMessageType.UNREAD.toString());
        chatMessage.setMessage(message);
        chatMessage.setUserSender(userRepository.findById(userId));
        chatMessage.setDate(LocalDateTime.now());
        chatMessage.setMessageAttachments(attachSaver.saveFiles(chatMessage, roomId, files));
        chatMessageRepository.save(chatMessage);

        ChatRoom room = chatRoomRepository.getChatRoomById(roomId);
        chatRoomRepository.updateRoomLastMessageData(room,chatMessage.getMessage(),chatMessage.getDate());

        List<ChatMember> allUsersInChat = chatMemberRepository.findAllIdUserByIdChat(roomId);

        for (ChatMember roomOwner : allUsersInChat) {
            MessageStatus status = new MessageStatus();

            status.setUser(userRepository.findById(roomOwner.getIdUser()));
            status.setMessage(chatMessage);
            status.setStatus(ChatMessageType.UNREAD.toString());
            messageStatusRepository.save(status);
        }
    }
}
