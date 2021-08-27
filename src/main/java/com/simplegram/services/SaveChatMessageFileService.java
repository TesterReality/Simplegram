package com.simplegram.services;

import com.simplegram.config.ConfigProperties;
import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.ChatMessageAttachments;
import com.simplegram.entity.enums.ChatMessageAttachmentsType;
import com.simplegram.exceptions.BadRequestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Log4j2
@Component
public class SaveChatMessageFileService {
    private final ConfigProperties config;

    //@Transactional(propagation = Propagation.REQUIRES_NEW )
    public Set<ChatMessageAttachments> saveFiles(ChatMessage chatMessage, String roomId, List<MultipartFile> files) {
        Set<ChatMessageAttachments> attachments = new HashSet<>();
        for (MultipartFile file : files) {
            try {
                File filesDir = Paths.get(config.getUploadPath(), "chatDownloads", roomId).toFile();
                FileUtils.createParentDirectories(filesDir);

                String newFilename = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
                FileUtils.createParentDirectories(Paths.get(filesDir.getAbsolutePath(), newFilename).toFile());
                file.transferTo(Paths.get(filesDir.getAbsolutePath(), newFilename));

                ChatMessageAttachments attach = new ChatMessageAttachments();
                attach.setMessage(chatMessage);
                attach.setType(getTypeAttachments(file));
                attach.setFileName(file.getOriginalFilename());
                attach.setUrl(newFilename);

                attachments.add(attach);
            } catch (IOException e) {
                log.error(e, e);
                throw new BadRequestException("exception.attachment");
            }
        }
        return attachments;
    }

    private String getTypeAttachments(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename()).matches("gif|jpe?g|tiff?|png|webp|bmp|jpg") ?
                ChatMessageAttachmentsType.IMAGE.toString() : ChatMessageAttachmentsType.FILE.toString();
    }
}
