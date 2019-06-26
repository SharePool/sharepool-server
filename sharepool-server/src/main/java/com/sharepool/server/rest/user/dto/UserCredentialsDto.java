package com.sharepool.server.rest.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpHeaders;

@ApiModel(value = "UserCredentials", description = "The credentials for the user.")
public class UserCredentialsDto {

    @ApiModelProperty(value = "The users token which needs to be used for follow up " +
            "requests via the " + HttpHeaders.AUTHORIZATION + " header.")
    private String userToken;

    @ApiModelProperty(value = "The users unique id.")
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
