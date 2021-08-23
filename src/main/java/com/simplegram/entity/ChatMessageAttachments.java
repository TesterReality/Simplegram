package com.simplegram.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chat_message_attachments")
public class ChatMessageAttachments {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @NotBlank
    @Column(name = "id_message")
    private String idMessage;

    @NotBlank
    private String url;

    @NotBlank
    private String type;
}
