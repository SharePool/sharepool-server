package com.sharepool.server.rest.user;

public class UserRestErrorMessages {

    private UserRestErrorMessages() {
    }

    public static String noUserFoundForEmail(String email) {
        return String.format("No User with email %s found", email);
    }

    public static String userWithEmailAlreadyExists(String email) {
        return String.format("User with email %s already exists. Please specify another one", email);
    }
}
