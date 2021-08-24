package com.simplegram.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_message")
    private ChatMessage message;

    @NotBlank
    private String url;

    @NotBlank
    private String type;
}
