package com.sharepool.server.logic.user;

import com.sharepool.server.common.AbstractUtilTest;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.UserRestErrorMessages;
import com.sharepool.server.rest.user.dto.UserCredentialsDto;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.user.dto.UserLoginDto;
import com.sharepool.server.rest.user.dto.UserUpdateDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRestRequestHandlerTest extends AbstractUtilTest {

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
                UserRestErrorMessages.userWithUserNameOrEmailAlreadyExists(userDto.getUserName(), userDto.getEmail()));
    }

    @Test
    public void testLoginWorksWithEmailOrUsername() {
        UserDto userDto = createValidUserDto();
        userRestRequestHandler.registerUser(userDto);

        UserLoginDto loginWithEmail = new UserLoginDto(userDto.getEmail(), userDto.getPassword());
        UserCredentialsDto userCredentialsDto = userRestRequestHandler.loginUser(loginWithEmail);
        assertNotNull(userCredentialsDto);

        UserLoginDto loginWithUserName = new UserLoginDto(userDto.getUserName(), userDto.getPassword());
        userCredentialsDto = userRestRequestHandler.loginUser(loginWithUserName);
        assertNotNull(userCredentialsDto);
    }

    @Test
    public void testUpdateUserInfo() {
        User oldUser = createValidUser();
        userRepository.save(oldUser);

        UserUpdateDto userUpdateDto = new UserUpdateDto("newUserName", "ne@mail.com", 1.0, "newImg".getBytes());
        userRestRequestHandler.updateUserInfo(oldUser, userUpdateDto);

        User updatedUser = userRepository.findById(oldUser.getId()).get();
        assertEquals(userUpdateDto.getUserName(), updatedUser.getUserName());
        assertEquals(userUpdateDto.getEmail(), updatedUser.getEmail());
        assertEquals(userUpdateDto.getGasConsumption(), updatedUser.getGasConsumption());
        assertEquals(userUpdateDto.getProfileImg(), updatedUser.getProfileImg());
    }
}