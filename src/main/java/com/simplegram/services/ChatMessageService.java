package com.simplegram.services;

import com.simplegram.entity.ChatMessage;
import com.simplegram.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void saveChatMessage(ChatMessage chatMessage)
    {
        chatMessageRepository.save(chatMessage);
    }


}
