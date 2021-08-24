package com.simplegram.services;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatRoom;
import com.simplegram.repository.ChatRoomRepository;
import com.simplegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberService chatMemberService;
    private final UserDetailsServiceImpl userDetailsService;

    public void createChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);

        ChatMember chatMember = new ChatMember();
        chatMember.setIdChat(chatRoom.getId());
        chatMember.setIdUser(chatRoom.getCreator().getId());

        chatMemberService.saveChatMember(chatMember);
    }

    public void addUsersFromLoginToChatRoom(String chatID, List<String> userLogin) {
        for (String login : userLogin) {
            if (userDetailsService.existsByLogin(login)) {
                ChatMember chatMember = new ChatMember();
                chatMember.setIdChat(chatID);
                chatMember.setIdUser(userDetailsService.findByLogin(login).get().getId());
                chatMemberService.saveChatMember(chatMember);
            }
        }
    }

    public boolean isChatRoomExist(String uuid) {
        return chatRoomRepository.existsById(uuid);
    }

    public String getTitleOfRoomId(String roomId,String userId) {
        int countRoomUsers = chatMemberService.getCountUsersInRoom(roomId);
        if(countRoomUsers>2)
        {
            return chatRoomRepository.getChatRoomById(roomId).getName();
        }
        return userDetailsService.findById(chatMemberService.getOtherUserInRoom(roomId,userId)).getLogin();
    }

    public boolean isGroupChat(String roomId)
    {
        int countRoomUsers = chatMemberService.getCountUsersInRoom(roomId);
        return countRoomUsers > 2;
    }
}
