package com.simplegram.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String login;
    private String avatar;
    private List<String> roles;

    public JwtResponse(String token, String id, String username, String login, String avatar, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.login = login;
        this.avatar = avatar;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

}
