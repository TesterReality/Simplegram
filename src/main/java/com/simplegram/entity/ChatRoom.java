package com.simplegram.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chat_room")
public class ChatRoom {
    @ManyToMany(mappedBy = "usersChats")
    Set<User> users;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    Set<ChatMessage> chatMessages = new HashSet<>();

    @Id
    @Column(name = "id", updatable = false, nullable = false, insertable = false)
    private String id = UUID.randomUUID().toString();

    @Size(min = 3, max = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    private String lastMessage;

    private LocalDateTime dateLastMessage;
}
