package com.sharepool.server.logic.user;

import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(source = "from", target = "fromLocation")
//    @Mapping(source = "to", target = "toLocation")
//    @Mapping(source = "cost", target = "tourCost")
//    AppUser loginUserDtoToAppUser(LoginUserDto loginUserDto);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    User registerUserDtoToAppUser(RegisterUserDto registerUserDto);
}
