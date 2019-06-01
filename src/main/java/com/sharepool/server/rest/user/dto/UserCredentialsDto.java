package com.sharepool.server.rest.user.dto;

public class UserCredentialsDto {

    private String userToken;
    private Long userId;

    public UserCredentialsDto() {
    }

    public UserCredentialsDto(String userToken, Long userId) {
        this.userToken = userToken;
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
