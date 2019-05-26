package com.sharepool.server.rest.user;

import javax.validation.Valid;

import com.sharepool.server.logic.user.UserRestRequestHandler;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserResource {

    private final UserRestRequestHandler requestHandler;

    @Autowired
    public UserResource(UserRestRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @PutMapping
    public String registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return requestHandler.registerUser(registerUserDto);
    }

    @PostMapping
    public String loginUser(@RequestBody @Valid LoginUserDto loginUserDto) {
        return requestHandler.loginUser(loginUserDto);
    }
}
