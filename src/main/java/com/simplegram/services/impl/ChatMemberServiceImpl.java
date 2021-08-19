package com.simplegram.services.impl;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.User;
import com.simplegram.repository.ChatMemberRepository;
import com.simplegram.repository.UserRepository;
import com.simplegram.services.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMemberServiceImpl implements ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;
    private final UserRepository userRepository;

    @Override
    public void saveChatMember(ChatMember chatMember) {
        chatMemberRepository.save(chatMember);
    }

    @Override
    public List<User> getAllUserByRoomID(String chatRoomUUID) {
        List<String> allUserIDInRoom;
        List<User> allUserInRoom = new ArrayList<>();

        allUserIDInRoom = chatMemberRepository.findUsersIDByChatRoomID(chatRoomUUID);

        for (String s : allUserIDInRoom) {
            allUserInRoom.add(userRepository.findById(s));
        }
        return allUserInRoom;
    }

    @Override
    public List<String> getAllRoomIDByUserID(String userUUID) {
        return chatMemberRepository.findAllRoomIDByUserUUID(userUUID);
    }
}
