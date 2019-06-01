package com.sharepool.server.rest.util.auth;

import com.sharepool.server.domain.User;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserContext {

    private Supplier<String> userToken;
    private Supplier<User> user;

    public String getUserToken() {
        return userToken.get();
    }

    public void setUserToken(Supplier<String> userToken) {
        this.userToken = userToken;
    }

    public User getUser() {
        return user.get();
    }

    public void setUser(Supplier<User> user) {
        this.user = user;
    }
}
