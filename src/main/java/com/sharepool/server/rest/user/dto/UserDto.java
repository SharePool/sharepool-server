package com.sharepool.server.rest.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

@ApiModel(value = "User", description = "The detailed information of the user.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @ApiModelProperty(value = "The users first name.", required = true)
    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;

    @ApiModelProperty(value = "The users last name.", required = true)
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @ApiModelProperty(value = "The users unique username.", required = true)
    @NotNull
    @Size(min = 5, max = 20)
    private String userName;

    @ApiModelProperty(value = "The users unique email.", required = true)
    @NotNull
    @Email
    private String email;

    @ApiModelProperty(value = "The users password.", required = true)
    @NotNull
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$")
    private String password;

    private byte[] profileImg;

    // l per 100 km
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
