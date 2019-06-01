package com.sharepool.server.logic.user;

import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.rest.user.UserRestErrorMessages;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
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
        RegisterUserDto registerUserDto = createValidRegisterUserDto();

        UserCredentialsDto userToken = userRestRequestHandler.registerUser(registerUserDto);

        Assert.assertNotNull(userToken);

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(registerUserDto.getEmail());
        loginUserDto.setPassword("wrongpassword");

        assertThrows(ResponseStatusException.class,
                () -> userRestRequestHandler.loginUser(loginUserDto));
    }

    @Test
    public void testLoginUser() {
        RegisterUserDto registerUserDto = createValidRegisterUserDto();

        UserCredentialsDto userToken = userRestRequestHandler.registerUser(registerUserDto);

        Assert.assertNotNull(userToken);

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(registerUserDto.getEmail());
        loginUserDto.setPassword("password");

        UserCredentialsDto loginUserToken = userRestRequestHandler.loginUser(loginUserDto);

        Assert.assertNotNull(loginUserToken);
    }

    private RegisterUserDto createValidRegisterUserDto() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setFirstName("firstname");
        registerUserDto.setLastName("lastname");
        registerUserDto.setUserName("userName");
        registerUserDto.setEmail("test@email.com");
        registerUserDto.setPassword("password");
        return registerUserDto;
    }

    @Test
    public void testEmailIsUnique() {
        RegisterUserDto registerUserDto = createValidRegisterUserDto();

        userRestRequestHandler.registerUser(registerUserDto);

        assertThrows(ResponseStatusException.class,
                () -> userRestRequestHandler.registerUser(registerUserDto),
                UserRestErrorMessages.userWithEmailAlreadyExists(registerUserDto.getEmail()));
    }
}