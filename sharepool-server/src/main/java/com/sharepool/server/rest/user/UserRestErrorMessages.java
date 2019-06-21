package com.sharepool.server.rest.user;

public class UserRestErrorMessages {

    private UserRestErrorMessages() {
    }

    public static String noUserFoundForEmail(String email) {
        return String.format("No User with email %s found", email);
    }

    public static String userWithUserNameOrEmailAlreadyExists(String userName, String email) {
        return String.format("User with username %s or email %s already exists. Please specify another one.", userName, email);
    }
}
