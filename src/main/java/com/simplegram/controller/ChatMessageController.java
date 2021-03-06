package com.simplegram.controller;

import com.simplegram.entity.ChatMember;
import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.ChatRoom;
import com.simplegram.entity.MessageStatus;
import com.simplegram.entity.enums.ChatMessageType;
import com.simplegram.exceptions.BadRequestException;
import com.simplegram.exceptions.UnauthorizedException;
import com.simplegram.payload.response.GetMessageFromChatResponse;
import com.simplegram.repository.*;
import com.simplegram.security.services.UserDetailsImpl;
import com.simplegram.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class ChatMessageController {
    private final ChatMemberService chatMemberService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageAttachmentsService chatAttachmentsService;
    private final MessageStatusService messageStatusService;
    private final SendMessageService sendMessageService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<GetMessageFromChatResponse>> getMessagesFromChatRoom(@RequestParam("sel") String roomID) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        checkRoomAvailable(roomID, userDetails.getId());

        List<GetMessageFromChatResponse> allMessages = new ArrayList<>();

        List<ChatMessage> chatMessages = chatMemberService.getAllSortedByDateMessageInRoom(roomID, 0, 10);

        for (ChatMessage chatMessage : chatMessages) {
            GetMessageFromChatResponse message = new GetMessageFromChatResponse();
            message.setLoginSender(chatMessage.getUserSender().getLogin());
            message.setMessage(chatMessage.getMessage());
            message.setSendDate(chatMessage.getDate());
            message.setAttachments(chatAttachmentsService.getAllChatAttachmentByIdMessage(chatMessage.getId()));
            message.setRead(messageStatusService.isMessageReadUsers(chatMessage.getId(), userDetails.getId()));

            allMessages.add(message);
        }

        return ResponseEntity
                .ok()
                .body(allMessages);
    }

    @PostMapping("/send")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> sendMessageToRoom(@RequestParam("roomId") String roomId,
                                                        @RequestParam(name = "message", required = false) String message,
                                                        @RequestPart(name = "file", required = false) List<MultipartFile> files) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        checkRoomAvailable(roomId, userDetails.getId());
        if (message.isEmpty() && files.size() == 0)
            throw new BadRequestException("exception.empty-message");

        sendMessageService.sendMessage(roomId,message,files,userDetails.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkRoomAvailable(String roomId, String userId) {
        if (!chatRoomService.isChatRoomExist(roomId)) {
            throw new BadRequestException("exception.room-not-found");
        }
        if (!chatMemberService.isUserAlreadyExitsInChat(roomId, userId)) {
            throw new UnauthorizedException("exception.access-denied");
        }
    }
}
