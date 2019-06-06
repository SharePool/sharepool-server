package com.sharepool.server.rest.user.dto;

import com.sharepool.server.common.dto.BaseDtoValidationTest;
import org.junit.Test;

public class UserDtoValidationTest extends BaseDtoValidationTest {

    @Test
    public void testNullFields() {
        LoginUserDto loginUserDto = new LoginUserDto(null, null);
        assertContainsViolation(loginUserDto, "email", 2);
        assertContainsViolation(loginUserDto, "password", 2);

        UserDto userDto = new UserDto(null, null, null, null, null);

        assertContainsViolation(userDto, "firstName");
        assertContainsViolation(userDto, "lastName");
        assertContainsViolation(userDto, "userName");
        assertContainsViolation(userDto, "email");
        assertContainsViolation(userDto, "password");
    }

    @Test
    public void testFieldLengths() {
        UserDto userDto = new UserDto("f", "l", "user", "a@m.at", "1*aB");

        assertContainsViolation(userDto, "firstName");
        assertContainsViolation(userDto, "lastName");
        assertContainsViolation(userDto, "userName");
        assertContainsViolation(userDto, "password");
    }

    @Test
    public void testFieldPatterns() {
        UserDto userDto = new UserDto("firstName", "lastName", "username", "a.at", "asdASD*213");

        assertContainsViolation(userDto, "email");
        assertContainsViolation(userDto, "password");
    }

}
