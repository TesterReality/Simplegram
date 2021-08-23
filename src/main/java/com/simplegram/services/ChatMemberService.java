package com.simplegram.services;

import com.simplegram.entity.ChatMember;
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
        List<String> allUserIDInRoom;
        List<User> allUserInRoom = new ArrayList<>();

        allUserIDInRoom = chatMemberRepository.findUsersIDByChatRoomID(chatRoomId);

        for (String s : allUserIDInRoom) {
            allUserInRoom.add(userRepository.findById(s));
        }
        return allUserInRoom;
    }

    public List<String> getAllRoomIDByUserID(String userId) {
        return chatMemberRepository.findAllRoomIDByUserUUID(userId);
    }

    public boolean isUserAlreadyExitsInChat(String chatRoomUUID, String userLogin)
    {
        return chatMemberRepository.isUserAlreadyExitsInChat(chatRoomUUID,userLogin);
    }

    public int getCountUsersInRoom(String roomID)
    {
        return chatMemberRepository.getCountUsersInRoom(roomID);
    }

    public String getOtherUserInRoom(String roomId, String userId)
    {
        return chatMemberRepository.getOtherUserInRoom(roomId,userId);
    }
}
