package com.sharepool.server.logic.user;

import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.UserRestErrorMessages;
import com.sharepool.server.rest.user.dto.UserCredentialsDto;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.user.dto.UserLoginDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRestRequestHandlerTest {

    @Autowired
    private UserRestRequestHandler userRestRequestHandler;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testLoginUserWrongPassword() {
        UserDto userDto = createValidUserDto();

        UserCredentialsDto userToken = userRestRequestHandler.registerUser(userDto);

        Assert.assertNotNull(userToken);

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserNameOrEmail(userDto.getEmail());
        userLoginDto.setPassword("wrongpassword");

        assertThrows(ResponseStatusException.class,
                () -> userRestRequestHandler.loginUser(userLoginDto));
    }

    private UserDto createValidUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstname");
        userDto.setLastName("lastname");
        userDto.setUserName("userName");
        userDto.setEmail("test@email.com");
        userDto.setPassword("password");
        return userDto;
    }

    @Test
    public void testLoginUser() {
        UserDto userDto = createValidUserDto();

        UserCredentialsDto userToken = userRestRequestHandler.registerUser(userDto);

        Assert.assertNotNull(userToken);

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserNameOrEmail(userDto.getEmail());
        userLoginDto.setPassword("password");

        UserCredentialsDto loginUserToken = userRestRequestHandler.loginUser(userLoginDto);

        Assert.assertNotNull(loginUserToken);
    }

    @Test
    public void testEmailIsUnique() {
        UserDto userDto = createValidUserDto();

        userRestRequestHandler.registerUser(userDto);

        assertThrows(ResponseStatusException.class,
                () -> userRestRequestHandler.registerUser(userDto),
                UserRestErrorMessages.userWithEmailAlreadyExists(userDto.getEmail()));
    }

    @Test
    public void testLoginWorksWithEmailOrUsername() {
        UserDto userDto = createValidUserDto();
        User user = userMapper.userDtoToUser(userDto);

        userRepository.save(user);

        UserLoginDto foundUser = new UserLoginDto(userDto.getEmail(), userDto.getPassword());
        assertNotNull(foundUser);

        foundUser = new UserLoginDto(userDto.getUserName(), userDto.getPassword());
        assertNotNull(foundUser);

    }
}