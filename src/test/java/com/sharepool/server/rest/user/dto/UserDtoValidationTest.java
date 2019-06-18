package com.sharepool.server.rest.user.dto;

import com.sharepool.server.common.dto.BaseDtoValidationTest;
import org.junit.Test;

public class UserDtoValidationTest extends BaseDtoValidationTest {

    @Test
    public void testNullFields() {
        UserLoginDto userLoginDto = new UserLoginDto(null, null);
        assertContainsViolation(userLoginDto, "userNameOrEmail", 2);
        assertContainsViolation(userLoginDto, "password", 2);

        UserDto userDto = new UserDto(null, null, null, null, null, null, null);

        assertContainsViolation(userDto, "firstName");
        assertContainsViolation(userDto, "lastName");
        assertContainsViolation(userDto, "userName");
        assertContainsViolation(userDto, "email");
        assertContainsViolation(userDto, "password");
        assertContainsViolation(userDto, "gasConsumption");
    }

    @Test
    public void testFieldLengths() {
        UserDto userDto = new UserDto("f", "l", "user", "a@m.at", "1*aB", null, 1.0);

        assertContainsViolation(userDto, "firstName");
        assertContainsViolation(userDto, "lastName");
        assertContainsViolation(userDto, "userName");
        assertContainsViolation(userDto, "password");
    }

    @Test
    public void testFieldPatterns() {
        UserDto userDto = new UserDto("firstName", "lastName", "username", "a.at", "asdASD*213", null, -1.0);

        assertContainsViolation(userDto, "email");
        assertContainsViolation(userDto, "password");
        assertContainsViolation(userDto, "gasConsumption");
    }

}
