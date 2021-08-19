package com.simplegram.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Entity
@Table(name = "chat_member")
public class ChatMember {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    private String id_user;

    @NotBlank
    private String id_chat;
}
