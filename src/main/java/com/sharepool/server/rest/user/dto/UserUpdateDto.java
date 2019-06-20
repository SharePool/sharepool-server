package com.sharepool.server.rest.user.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class UserUpdateDto {

    @ApiModelProperty(value = "The users unique username.", required = true)
    @NotNull
    @Size(min = 5, max = 20)
    private String userName;

    @ApiModelProperty(value = "The users unique email.", required = true)
    @NotNull
    @Email
    private String email;

    @ApiModelProperty(value = "The user's car's average gas consumption over 100 km in liters.", required = true)
    @NotNull
    @PositiveOrZero
    private Double gasConsumption;

    @ApiModelProperty(value = "The user's profile image as base64 encoded byte-array.")
    private byte[] profileImg;

    public UserUpdateDto() {
    }

    public UserUpdateDto(String userName, String email, Double gasConsumption, byte[] profileImg) {
        this.userName = userName;
        this.email = email;
        this.gasConsumption = gasConsumption;
        this.profileImg = profileImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getGasConsumption() {
        return gasConsumption;
    }

    public void setGasConsumption(Double gasConsumption) {
        this.gasConsumption = gasConsumption;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }
}
