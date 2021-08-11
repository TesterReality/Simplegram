package com.simplegram.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplegram.entity.Role;
import com.simplegram.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final String username;
    private final String login;
    @JsonIgnore
    private final String password;
    private final String avatar;
    private final String role;

    public UserDetailsImpl(String id, String username,
                           String login, String password,
                           String avatar, String role) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword(),
                user.getAvatar(), Role.ROLE_USER.toString());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
