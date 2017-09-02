package me.imunize.imunizeme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sr. Décio Montanhani on 20/08/2017.
 */

public class RespostaAutenticacao {

    private String token;
    @JsonProperty("user_info")
    private UserInfo userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
