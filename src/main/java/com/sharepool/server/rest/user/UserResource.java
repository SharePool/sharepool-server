package com.sharepool.server.rest.user;

import com.sharepool.server.logic.user.UserRestRequestHandler;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserResource {

    private final UserRestRequestHandler requestHandler;

    @Autowired
    public UserResource(UserRestRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @PutMapping
    public ResponseEntity registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        String userToken = requestHandler.registerUser(registerUserDto);

        if (userToken == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(userToken);
    }

    @PostMapping
    public ResponseEntity loginUser(@RequestBody @Valid LoginUserDto loginUserDto) {
        return ResponseEntity.ok(requestHandler.loginUser(loginUserDto));
    }
}
