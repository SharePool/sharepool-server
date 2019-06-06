package com.sharepool.server.rest.user;

import com.sharepool.server.logic.user.UserRestRequestHandler;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.user.dto.UserCredentialsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserResource {

    private final UserRestRequestHandler requestHandler;

    public UserResource(UserRestRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @PutMapping
    public ResponseEntity registerUser(@RequestBody @Valid UserDto userDto) {
        UserCredentialsDto userCredentials = requestHandler.registerUser(userDto);

        if (userCredentials == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(userCredentials);
    }

    @PostMapping
    public ResponseEntity loginUser(@RequestBody @Valid LoginUserDto loginUserDto) {
        UserCredentialsDto userCredentials = requestHandler.loginUser(loginUserDto);

        return ResponseEntity.ok(userCredentials);
    }
}
