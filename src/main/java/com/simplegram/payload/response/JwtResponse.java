package com.simplegram.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String login;
    private String avatar;

    public JwtResponse(String token, String id, String username, String login, String avatar) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.login = login;
        this.avatar = avatar;
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
