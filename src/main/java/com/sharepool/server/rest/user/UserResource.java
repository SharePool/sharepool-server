package com.sharepool.server.rest.user;

import com.sharepool.server.logic.user.UserRestRequestHandler;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import com.sharepool.server.rest.user.dto.UserTokenDto;
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
    public ResponseEntity registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        String userToken = requestHandler.registerUser(registerUserDto);

        if (userToken == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(new UserTokenDto(userToken));
    }

    @PostMapping
    public ResponseEntity loginUser(@RequestBody @Valid LoginUserDto loginUserDto) {
        String userToken = requestHandler.loginUser(loginUserDto);

        return ResponseEntity.ok(new UserTokenDto(userToken));
    }
}
