package com.sharepool.server.rest.user;

import com.sharepool.server.logic.user.UserRestRequestHandler;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.user.dto.UserCredentialsDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(tags = "Users", description = "Register or login a user.")
@RestController
@RequestMapping("users")
public class UserResource {

    private final UserRestRequestHandler requestHandler;

    public UserResource(UserRestRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @ApiOperation(
            value = "Registers a new user to the application"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Success. The user has been successfully created. " +
                    "The response contains the user token which should be used for follow up requests.",
                    response = UserCredentialsDto.class),
            @ApiResponse(code = 404, message = "Failed. The user with the email/username already exists."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @PutMapping
    public ResponseEntity registerUser(
            @ApiParam("The JSON body of the request. Contains parameters of user.")
            @RequestBody
            @Valid
                    UserDto userDto
    ) {
        UserCredentialsDto userCredentials = requestHandler.registerUser(userDto);

        if (userCredentials == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(userCredentials);
    }

    @ApiOperation(
            value = "Logs in an already existing user to the application"
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Success. The user has been successfully logged in.",
                    response = UserCredentialsDto.class),
            @ApiResponse(code = 401, message = "Failed. Password was incorrect."),
            @ApiResponse(code = 404, message = "Failed. User with username/email does not exists."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @PostMapping
    public ResponseEntity loginUser(
            @ApiParam("The JSON body of the request. Contains parameters of the user login.")
            @RequestBody
            @Valid
                    LoginUserDto loginUserDto
    ) {
        UserCredentialsDto userCredentials = requestHandler.loginUser(loginUserDto);

        return ResponseEntity.ok(userCredentials);
    }
}
