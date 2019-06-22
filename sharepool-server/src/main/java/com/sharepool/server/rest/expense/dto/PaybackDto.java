package com.sharepool.server.rest.expense.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Payback", description = "A payback from a user to another one which is not linked to specific tour.")
public class PaybackDto {

    @ApiModelProperty("The username or email of the paybacks receiving user.")
    private String userNameOrEmail;

    @ApiModelProperty("The amount to pay back to the receiving user.")
    private double amount;

    public String getUserNameOrEmail() {
        return userNameOrEmail;
    }

    public void setUserNameOrEmail(String userNameOrEmail) {
        this.userNameOrEmail = userNameOrEmail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
