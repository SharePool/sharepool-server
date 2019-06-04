package com.sharepool.server.rest.user.dto;

import com.sharepool.server.common.dto.BaseDtoValidationTest;
import org.junit.Test;

public class UserDtoValidationTest extends BaseDtoValidationTest {

    @Test
    public void testNullFields() {
        LoginUserDto loginUserDto = new LoginUserDto(null, null);
        assertContainsViolation(loginUserDto, "email", 2);
        assertContainsViolation(loginUserDto, "password", 2);

        RegisterUserDto registerUserDto = new RegisterUserDto(null, null, null, null, null);

        assertContainsViolation(registerUserDto, "firstName");
        assertContainsViolation(registerUserDto, "lastName");
        assertContainsViolation(registerUserDto, "userName");
        assertContainsViolation(registerUserDto, "email");
        assertContainsViolation(registerUserDto, "password");
    }

    @Test
    public void testFieldLengths() {
        RegisterUserDto registerUserDto = new RegisterUserDto("f", "l", "user", "a@m.at", "1*aB");

        assertContainsViolation(registerUserDto, "firstName");
        assertContainsViolation(registerUserDto, "lastName");
        assertContainsViolation(registerUserDto, "userName");
        assertContainsViolation(registerUserDto, "password");
    }

    @Test
    public void testFieldPatterns() {
        RegisterUserDto registerUserDto = new RegisterUserDto("firstName", "lastName", "username", "a.at", "asdASD*213");

        assertContainsViolation(registerUserDto, "email");
        assertContainsViolation(registerUserDto, "password");
    }

}
