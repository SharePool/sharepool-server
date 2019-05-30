package com.sharepool.server.logic.user;

import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import com.sharepool.server.rest.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User registerUserDtoToUser(RegisterUserDto registerUserDto);

    UserDto userToUserDto(User user);
}
