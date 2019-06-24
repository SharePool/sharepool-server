package com.sharepool.server.rest.user;

import com.sharepool.server.domain.User;
import com.sharepool.server.logic.user.UserRestRequestHandler;
import com.sharepool.server.rest.user.dto.UserCredentialsDto;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.user.dto.UserLoginDto;
import com.sharepool.server.rest.user.dto.UserUpdateDto;
import com.sharepool.server.rest.util.auth.UserContext;
import io.swagger.annotations.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(tags = "Users", description = "Register or login a user.")
@RestController
@RequestMapping("users")
public class UserResource {

    private final UserRestRequestHandler requestHandler;
    private final UserContext userContext;

    public UserResource(
            UserRestRequestHandler requestHandler,
            UserContext userContext
    ) {
        this.requestHandler = requestHandler;
        this.userContext = userContext;
    }

    @ApiOperation(
            value = "Registers a new user to the application."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The user has been successfully created. " +
                    "The response contains the user token which should be used for follow up requests.",
                    response = UserCredentialsDto.class),
            @ApiResponse(code = 404, message = "Failed. The user with the email/username already exists."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCredentialsDto> registerUser(
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
            value = "Update user's data."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The user info has been successfully updated. "),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(
            @ApiParam("The JSON body of the request. Contains parameters of user.")
            @RequestBody
            @Valid
                    UserUpdateDto userDto
    ) {
        User oldUser = userContext.getUser();
        requestHandler.updateUserInfo(oldUser, userDto);
        return ResponseEntity.ok(null);
    }

    @ApiOperation(
            value = "Logs in an already existing user to the application."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The user has been successfully logged in.",
                    response = UserCredentialsDto.class),
            @ApiResponse(code = 401, message = "Failed. Password was incorrect."),
            @ApiResponse(code = 404, message = "Failed. User with username/email does not exists."),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @PutMapping(path = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCredentialsDto> loginUser(
            @ApiParam("The JSON body of the request. Contains parameters of the user login.")
            @RequestBody
            @Valid
                    UserLoginDto userLoginDto
    ) {
        UserCredentialsDto userCredentials = requestHandler.loginUser(userLoginDto);

        return ResponseEntity.ok(userCredentials);
    }

    @ApiOperation(
            value = "Retrieves the information for the logged in user. " +
                    "This information is extracted from the " + HttpHeaders.AUTHORIZATION + " header."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The users information will be presented.",
                    response = UserDto.class),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserInfo() {
        return ResponseEntity.ok(requestHandler.getUserInfo(userContext));
    }

    @ApiOperation(
            value = "Get the users context. Is used by the analytics service for authentication " +
                    "This information is extracted from the " + HttpHeaders.AUTHORIZATION + " header."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success. The users context presented.",
                    response = UserDto.class),
            @ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
    })
    @GetMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getUserId() {
        return ResponseEntity.ok(userContext.getUser().getId());
    }
}
