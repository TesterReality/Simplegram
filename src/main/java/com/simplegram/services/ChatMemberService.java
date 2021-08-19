package com.simplegram.services;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.User;

import java.util.List;

public interface ChatMemberService {
    void saveChatMember(ChatMember chatMember);
    List<User> getAllUserByRoomID(String chatRoomUUID);
    List<String> getAllRoomIDByUserID(String userUUID);

}
