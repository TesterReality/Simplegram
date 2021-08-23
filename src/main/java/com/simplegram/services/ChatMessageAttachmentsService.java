package com.simplegram.services;

import com.simplegram.entity.ChatMessageAttachments;
import com.simplegram.repository.ChatMessageAttachmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageAttachmentsService {
    private final ChatMessageAttachmentsRepository attachmentsRepository;

    public void saveAttachment(ChatMessageAttachments attachments)
    {
        attachmentsRepository.save(attachments);
    }
}
