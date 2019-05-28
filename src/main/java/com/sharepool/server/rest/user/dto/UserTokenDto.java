package com.sharepool.server.rest.user.dto;

public class UserTokenDto {

    private String userToken;

    public UserTokenDto() {
    }

    public UserTokenDto(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
