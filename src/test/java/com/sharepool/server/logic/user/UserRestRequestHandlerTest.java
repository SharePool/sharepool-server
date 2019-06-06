package com.sharepool.server.logic.user;

import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.rest.user.UserRestErrorMessages;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.user.dto.UserCredentialsDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRestRequestHandlerTest {

    @Autowired
    private UserRestRequestHandler userRestRequestHandler;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testLoginUserWrongPassword() {
        UserDto userDto = createValidRegisterUserDto();

        UserCredentialsDto userToken = userRestRequestHandler.registerUser(userDto);

        Assert.assertNotNull(userToken);

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(userDto.getEmail());
        loginUserDto.setPassword("wrongpassword");

        assertThrows(ResponseStatusException.class,
                () -> userRestRequestHandler.loginUser(loginUserDto));
    }

    @Test
    public void testLoginUser() {
        UserDto userDto = createValidRegisterUserDto();

        UserCredentialsDto userToken = userRestRequestHandler.registerUser(userDto);

        Assert.assertNotNull(userToken);

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(userDto.getEmail());
        loginUserDto.setPassword("password");

        UserCredentialsDto loginUserToken = userRestRequestHandler.loginUser(loginUserDto);

        Assert.assertNotNull(loginUserToken);
    }

    private UserDto createValidRegisterUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstname");
        userDto.setLastName("lastname");
        userDto.setUserName("userName");
        userDto.setEmail("test@email.com");
        userDto.setPassword("password");
        return userDto;
    }

    @Test
    public void testEmailIsUnique() {
        UserDto userDto = createValidRegisterUserDto();

        userRestRequestHandler.registerUser(userDto);

        assertThrows(ResponseStatusException.class,
                () -> userRestRequestHandler.registerUser(userDto),
                UserRestErrorMessages.userWithEmailAlreadyExists(userDto.getEmail()));
    }
}