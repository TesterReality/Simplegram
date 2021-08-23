package com.simplegram.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", updatable = false, nullable = false, insertable = false)
    private String id = UUID.randomUUID().toString();

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 3, max = 20)
    private String login;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Size(max = 140)
    private String avatar;

    @NotNull
    @Column(name = "online_date")
    private LocalDateTime onlineDate;

    @OneToMany(mappedBy = "creator",cascade = CascadeType.ALL)
    Set<ChatRoom> CreatorChatRooms = new HashSet<>();

    public User() {
    }
}
