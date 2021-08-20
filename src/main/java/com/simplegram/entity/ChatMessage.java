package com.simplegram.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @NotBlank
    @Column(name = "id_chat")
    private String chatId;

    @NotBlank
    @Column(name = "id_sender")
    private String senderId;

    @NotBlank
    private String message;

    @NotBlank
    private String type;//прочитано /не прочитано

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "date_delete")
    private LocalDateTime dateDelete;
}
