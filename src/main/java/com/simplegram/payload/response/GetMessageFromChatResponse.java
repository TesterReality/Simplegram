package com.simplegram.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GetMessageFromChatResponse {
    private String loginSender;
    private String message;
    private LocalDateTime sendDate;
    private boolean isRead;
    private List<AttachmentsMessage> attachments;
}
