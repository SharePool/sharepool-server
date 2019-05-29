package com.sharepool.server.rest.user.dto;

import javax.validation.constraints.NotNull;

public class UserDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String userName;

    public UserDto() {
    }

    public UserDto(@NotNull String firstName, @NotNull String lastName, @NotNull String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
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
}
