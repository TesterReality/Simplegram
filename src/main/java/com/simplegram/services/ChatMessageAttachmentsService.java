package com.simplegram.services;

import com.simplegram.entity.ChatMessageAttachments;
import com.simplegram.payload.response.AttachmentsMessage;
import com.simplegram.repository.ChatMessageAttachmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageAttachmentsService {
    private final ChatMessageAttachmentsRepository attachmentsRepository;

    public void saveAttachment(ChatMessageAttachments attachments)
    {
        attachmentsRepository.save(attachments);
    }

    public List<AttachmentsMessage> getAllChatAttachmentByIdMessage(String messageId)
    {
        List<AttachmentsMessage> attachmentsMessages = new ArrayList<>();
        List<ChatMessageAttachments> chatMessageAttachments = attachmentsRepository.findAllByMessage_Id(messageId);

        for (ChatMessageAttachments chatMessageAttachment : chatMessageAttachments) {
            AttachmentsMessage attach = new AttachmentsMessage();
            attach.setType(chatMessageAttachment.getType());
            attach.setUrl(chatMessageAttachment.getUrl());
            attachmentsMessages.add(attach);
        }

        return attachmentsMessages;
    }
}
