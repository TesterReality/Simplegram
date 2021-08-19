package com.simplegram.services.impl;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatRoom;
import com.simplegram.repository.ChatRoomRepository;
import com.simplegram.services.ChatMemberService;
import com.simplegram.services.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberService chatMemberService;

    @Override
    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        ChatMember chatMember = new ChatMember();
        chatMember.setId_chat(chatRoom.getId());
        chatMember.setId_user(chatRoom.getCreator());

        chatMemberService.saveChatMember(chatMember);

        return chatRoomRepository.save(chatRoom);
    }

}
