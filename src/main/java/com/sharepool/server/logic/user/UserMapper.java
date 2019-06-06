package com.sharepool.server.logic.user;

import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
