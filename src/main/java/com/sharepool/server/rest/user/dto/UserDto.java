package com.sharepool.server.rest.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sharepool.server.rest.user.validation.FirstName;
import com.sharepool.server.rest.user.validation.LastName;
import com.sharepool.server.rest.user.validation.Password;
import com.sharepool.server.rest.user.validation.UserName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@ApiModel(value = "User", description = "The detailed information of the user.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @ApiModelProperty(value = "The users first name.", required = true)
    @NotNull
    @FirstName
    private String firstName;

    @ApiModelProperty(value = "The users last name.", required = true)
    @NotNull
    @LastName
    private String lastName;

    @ApiModelProperty(value = "The users unique username.", required = true)
    @NotNull
    @UserName
    private String userName;

    @ApiModelProperty(value = "The users unique email.", required = true)
    @NotNull
    @Email
    private String email;

    @ApiModelProperty(value = "The users password.", required = true)
    @NotNull
    @Password
    private String password;

    public UserDto() {
    }

    public UserDto(@NotNull String firstName, @NotNull String lastName, @NotNull String userName, @NotNull @Email String email, @NotNull String password) {
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
