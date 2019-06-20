package com.sharepool.server.logic.user;

import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.user.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    void updateUserFromUserUpdateDto(UserUpdateDto userUpdateDto, @MappingTarget User user);
}
