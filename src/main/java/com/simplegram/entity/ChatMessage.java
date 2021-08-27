package com.simplegram.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    Set<ChatMessageAttachments> messageAttachments = new HashSet<>();

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    Set<MessageStatus> messageStatuses = new HashSet<>();

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat", updatable = false, insertable = false)
    private ChatRoom chatRoom;

    @Column(name = "id_chat")
    private String chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sender")
    private User userSender;

    private String message;

    @NotBlank
    private String type;//прочитано /не прочитано

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "date_delete")
    private LocalDateTime dateDelete;
}
