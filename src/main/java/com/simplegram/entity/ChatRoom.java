package com.simplegram.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @Column(name = "id", updatable = false, nullable = false, insertable = false)
    private String id = UUID.randomUUID().toString();

    @Size(min = 3, max = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User creator;

}
