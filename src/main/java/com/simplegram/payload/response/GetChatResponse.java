package com.simplegram.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetChatResponse {
    private String title;
    private String lastMessage;
    private LocalDateTime timeLastMessage;
    private boolean isGroup;
    private int countUnreadMessage;
    private String chatId;
    private String avatars;
}
