package com.simplegram.services;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatRoom;
import com.simplegram.entity.User;
import com.simplegram.repository.ChatMemberRepository;
import com.simplegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;
    private final UserRepository userRepository;

    public void saveChatMember(ChatMember chatMember) {
        chatMemberRepository.save(chatMember);
    }

    public List<User> getAllUserByRoomID(String chatRoomId) {
        List<ChatMember> allUserIDInRoom;
        List<User> allUserInRoom = new ArrayList<>();

        allUserIDInRoom = chatMemberRepository.findAllIdUserByIdChat(chatRoomId);

        for (ChatMember s : allUserIDInRoom) {
            allUserInRoom.add(userRepository.findById(s.getIdUser()));
        }
        return allUserInRoom;
    }

    public boolean isUserAlreadyExitsInChat(String chatRoomUUID, String userId)
    {
        return chatMemberRepository.findAllByIdUserAndIdChat(userId, chatRoomUUID).size()>0;
    }

    public int getCountUsersInRoom(String roomID)
    {
        return chatMemberRepository.findAllIdUserByIdChat(roomID).size();
    }

    public String getOtherUserInRoom(String roomId, String userId)
    {
        return chatMemberRepository.findAllByIdChatAndIdUserNotContaining(roomId,userId).get(0).getIdUser();
    }

    public List<ChatMember> getAllRoomIDByUserID(String userId)
    {
        return chatMemberRepository.findAllIdChatByIdUser(userId);
    }
}
