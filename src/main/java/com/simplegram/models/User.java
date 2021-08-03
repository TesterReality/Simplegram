package com.simplegram.models;

import org.hibernate.annotations.GenericGenerator;
import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "login")
        })
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String login;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 140)
    private String avatar;

    @NotNull
    private Timestamp online_data;

    public User() {
    }


    public User(String username, String login, String password, String avatar, Timestamp online_data) {
        this.username = username;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.online_data = online_data;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getOnline_data() {
        return online_data;
    }

    public void setOnline_data(Timestamp online_data) {
        this.online_data = online_data;
    }
}
