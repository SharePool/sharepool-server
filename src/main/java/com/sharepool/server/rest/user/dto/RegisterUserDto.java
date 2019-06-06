package com.sharepool.server.rest.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(value = "User", description = "The details of a specific user.")
public class RegisterUserDto {

    @ApiModelProperty(value = "The first name of the user.", required = true)
    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;

    @ApiModelProperty(value = "The last name of the user.", required = true)
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @ApiModelProperty(value = "The username of the user.", required = true)
    @NotNull
    @Size(min = 5, max = 20)
    private String userName;

    @ApiModelProperty(value = "The email of the user.", required = true)
    @NotNull
    @Email
    private String email;

    @ApiModelProperty(value = "The password of the user.", required = true)
    @NotNull
    @Size(min = 8, max = 25)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$")
    private String password;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
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
}
