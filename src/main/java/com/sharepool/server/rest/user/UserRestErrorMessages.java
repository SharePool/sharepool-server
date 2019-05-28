package com.sharepool.server.rest.user;

public class UserRestErrorMessages {

    private UserRestErrorMessages() {
    }

    public static String noUserFoundForEmail(String email) {
        return String.format("No User with email %s found", email);
    }
}
