package com.sharepool.server.rest.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

@ApiModel(value = "User", description = "The detailed information of the user.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @ApiModelProperty(value = "The user's first name.", required = true)
    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;

    @ApiModelProperty(value = "The user's last name.", required = true)
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @ApiModelProperty(value = "The user's unique username.", required = true)
    @NotNull
    @Size(min = 5, max = 20)
    private String userName;

    @ApiModelProperty(value = "The user's unique email.", required = true)
    @NotNull
    @Email
    private String email;

    @ApiModelProperty(value = "The user's password.", required = true)
    @NotNull
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$")
    private String password;

    @ApiModelProperty(value = "The user's profile image as base64 encoded byte-array.")
    private byte[] profileImg;

    @ApiModelProperty(value = "The user's car's average gas consumption over 100 km in liters.", required = true)
    @NotNull
    @PositiveOrZero
    private Double gasConsumption;

    public UserDto() {
    }

    public UserDto(@NotNull String firstName, @NotNull String lastName, @NotNull String userName, @NotNull @Email String email, @NotNull String password, byte[] profileImg, Double gasConsumption) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.gasConsumption = gasConsumption;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public Double getGasConsumption() {
        return gasConsumption;
    }

    public void setGasConsumption(Double gasConsumption) {
        this.gasConsumption = gasConsumption;
    }
}
