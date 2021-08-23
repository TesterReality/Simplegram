package com.simplegram.controller;

import com.simplegram.payload.response.GetChatResponse;
import com.simplegram.security.jwt.AuthTokenFilter;
import com.simplegram.security.services.UserDetailsImpl;
import com.simplegram.services.ChatMemberService;
import com.simplegram.services.ChatMessageService;
import com.simplegram.services.ChatRoomService;
import com.simplegram.services.MessageStatusService;
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

    @GetMapping("/getChat")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity authenticateUser() {

        List<GetChatResponse> responseAllChat = new ArrayList<>();

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> usersRoomId = chatMemberService.getAllRoomIDByUserID(userDetails.getId());

        for (String roomId : usersRoomId) {
            GetChatResponse chat = new GetChatResponse();
            chat.setTitle(chatRoomService.getTitleOfRoomId(roomId,userDetails.getId()));
            chat.setLastMessage(chatMessageService.getLastMessageFromRoomId(roomId));
            chat.setTimeLastMessage(chatMessageService.getDateLastMessageFromRoomId(roomId));
            chat.setGroup(chatRoomService.isGroupChat(roomId));
            chat.setCountUnreadMessage(messageStatusService.getCountUnreadMessageByRoomId(roomId,userDetails.getId()));
            chat.setChatId(roomId);


            responseAllChat.add(chat);
        }

        String test = "fdfd";

        return ResponseEntity
                .ok()
                .body(userDetails.getLogin());
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
