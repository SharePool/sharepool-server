package com.sharepool.server.rest.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@ApiModel(value = "UserLogin", description = "The credential of a specific user.")
public class LoginUserDto {

    @ApiModelProperty(value = "The email of the user.", required = true)
    @NotNull
    @Email
    private String email;

    @ApiModelProperty(value = "The password of the user.", required = true)
    @NotNull
    private String password;

    public LoginUserDto() {
    }

    public LoginUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
