package com.simplegram.controller;

import com.simplegram.entity.ChatMessage;
import com.simplegram.exceptions.BadRequestException;
import com.simplegram.exceptions.UnauthorizedException;
import com.simplegram.payload.response.GetMessageFromChatResponse;
import com.simplegram.security.jwt.AuthTokenFilter;
import com.simplegram.security.services.UserDetailsImpl;
import com.simplegram.services.ChatMemberService;
import com.simplegram.services.ChatMessageAttachmentsService;
import com.simplegram.services.ChatRoomService;
import com.simplegram.services.MessageStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ChatMessageController {
    private final ChatMemberService chatMemberService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageAttachmentsService chatAttachmentsService;
    private final MessageStatusService messageStatusService;

    @GetMapping("/message")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<GetMessageFromChatResponse>> sendMessage(@RequestParam("sel") String roomID) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!chatRoomService.isChatRoomExist(roomID)) {
            throw new BadRequestException("Chat Room Not Found");
        }
        if (!chatMemberService.isUserAlreadyExitsInChat(roomID, userDetails.getId())) {
            throw new UnauthorizedException("Access denied");
        }
        List<GetMessageFromChatResponse> allMessages = new ArrayList<>();

        List<ChatMessage> chatMessages = chatMemberService.getAllSortedByDateMessageInRoom(roomID,0,10);

        for (ChatMessage chatMessage : chatMessages) {
            GetMessageFromChatResponse message = new GetMessageFromChatResponse();
            message.setLoginSender(chatMessage.getUserSender().getLogin());
            message.setMessage(chatMessage.getMessage());
            message.setSendDate(chatMessage.getDate());
            message.setAttachments(chatAttachmentsService.getAllChatAttachmentByIdMessage(chatMessage.getId()));
            message.setRead(messageStatusService.isMessageReadUsers(chatMessage.getId(),userDetails.getId()));

            allMessages.add(message);
        }

        String test = "fdfd";

        return ResponseEntity
                .ok()
                .body(allMessages);
    }
}
