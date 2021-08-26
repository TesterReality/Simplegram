package com.simplegram.controller;

import com.simplegram.entity.ChatRoom;
import com.simplegram.entity.User;
import com.simplegram.payload.response.GetChatResponse;
import com.simplegram.repository.ChatRoomRepository;
import com.simplegram.security.services.UserDetailsImpl;
import com.simplegram.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chats")
public class ChatsController {
    private final ChatMemberService chatMemberService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final MessageStatusService messageStatusService;
    private final UserDetailsServiceImpl userDetailsService;
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/getChats")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<GetChatResponse>> authenticateUser() {
        List<GetChatResponse> responseAllChat = new ArrayList<>();

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ChatRoom> usersRooms = chatMemberService.getAllRoomsByUserID(userDetails.getId());

        for (ChatRoom userRoom : usersRooms) {
            GetChatResponse chat = new GetChatResponse();
            chat.setTitle(chatRoomService.getTitleOfRoomId(userRoom.getId(), userDetails.getId()));
            chat.setLastMessage(userRoom.getLastMessage());
            chat.setTimeLastMessage(userRoom.getDateLastMessage());

            chat.setGroup(chatRoomService.isGroupChat(userRoom.getId()));
            chat.setCountUnreadMessage(messageStatusService.getCountUnreadMessageByRoomId(userRoom.getId(), userDetails.getId()));
            chat.setChatId(userRoom.getId());
            if (!chat.isGroup()) {
                //значит приватный чат
                User userFriend = userDetailsService.findByLogin(chat.getTitle()).get();
                chat.setAvatars(userFriend.getAvatar());
                chat.setLastOnlineData(userFriend.getOnlineDate());
            }

            responseAllChat.add(chat);
        }

        return ResponseEntity
                .ok()
                .body(responseAllChat);
    }

    @PostMapping("/send")
    @PreAuthorize("isAuthenticated()")
    //Здесь отправляю сообщения для чата
    public ResponseEntity sendMessagetoGroupChat(@RequestParam("peers") String roomID, @RequestParam("message") String message) {

        String test = "fdfd";

        return ResponseEntity
                .ok()
                .body(roomID);
    }

}
