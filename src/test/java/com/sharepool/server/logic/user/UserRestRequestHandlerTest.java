package com.sharepool.server.logic.user;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRestRequestHandlerTest {

    @Autowired
    private UserRestRequestHandler userRestRequestHandler;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test(expected = HttpClientErrorException.class)
    public void testLoginUserWrongPassword() {
        RegisterUserDto registerUserDto = createValidRegisterUserDto();

        String userToken = userRestRequestHandler.registerUser(registerUserDto);

        Assert.assertNotNull(userToken);

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(registerUserDto.getEmail());
        loginUserDto.setPassword("wrongpassword");

        userRestRequestHandler.loginUser(loginUserDto);
    }

    private RegisterUserDto createValidRegisterUserDto() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setFirstName("firstname");
        registerUserDto.setLastName("lastname");
        registerUserDto.setUsername("userName");
        registerUserDto.setEmail("test@email.com");
        registerUserDto.setPassword("password");
        return registerUserDto;
    }

    @Test
    public void testLoginUser() {
        RegisterUserDto registerUserDto = createValidRegisterUserDto();

        String userToken = userRestRequestHandler.registerUser(registerUserDto);

        Assert.assertNotNull(userToken);

        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(registerUserDto.getEmail());
        loginUserDto.setPassword("password");

        String loginUserToken = userRestRequestHandler.loginUser(loginUserDto);

        Assert.assertNotNull(loginUserToken);
    }
}