package com.sharepool.server.logic.user;

import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import com.sharepool.server.rest.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    User registerUserDtoToAppUser(RegisterUserDto registerUserDto);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "userName", target = "userName")
    UserDto userToUserDto(User user);
}
