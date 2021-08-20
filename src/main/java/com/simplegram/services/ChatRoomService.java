package com.simplegram.services;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatRoom;
import com.simplegram.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberService chatMemberService;

    public void createChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);

        ChatMember chatMember = new ChatMember();
        chatMember.setIdChat(chatRoom.getId());
        chatMember.setIdUser(chatRoom.getCreator());

        chatMemberService.saveChatMember(chatMember);
    }

    public boolean isChatRoomExist(String uuid)
    {
        return chatRoomRepository.existsById(uuid);
    }

    public void addUserFromLoginToRoom(String userLogin)
    {

    }

    public boolean isUserAlreadyExistsInRoom(String userLogin,String roomUuid)
    {
        return false;
    }
}
