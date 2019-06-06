package com.sharepool.server.rest.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "UserLogin", description = "The credential of a specific user.")
public class UserLoginDto {

    @ApiModelProperty(value = "The username or email of the user.", required = true)
    @NotNull
    private String userNameOrEmail;

    @ApiModelProperty(value = "The password of the user.", required = true)
    @NotNull
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String userNameOrEmail, String password) {
        this.userNameOrEmail = userNameOrEmail;
        this.password = password;
    }

    public String getUserNameOrEmail() {
        return userNameOrEmail;
    }

    public void setUserNameOrEmail(String userNameOrEmail) {
        this.userNameOrEmail = userNameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
