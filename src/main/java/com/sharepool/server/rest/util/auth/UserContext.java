package com.sharepool.server.rest.util.auth;

import com.sharepool.server.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private String userToken;
    private User user;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
