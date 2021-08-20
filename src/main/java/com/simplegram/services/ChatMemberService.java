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

    public List<User> getAllUserByRoomID(String chatRoomUUID) {
        List<String> allUserIDInRoom;
        List<User> allUserInRoom = new ArrayList<>();

        allUserIDInRoom = chatMemberRepository.findUsersIDByChatRoomID(chatRoomUUID);

        for (String s : allUserIDInRoom) {
            allUserInRoom.add(userRepository.findById(s));
        }
        return allUserInRoom;
    }

    public List<String> getAllRoomIDByUserID(String userUUID) {
        return chatMemberRepository.findAllRoomIDByUserUUID(userUUID);
    }
}
