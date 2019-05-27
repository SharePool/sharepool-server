package com.sharepool.server.logic.user;

import com.sharepool.server.domain.AppUser;
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
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    AppUser registerUserDtoToAppUser(RegisterUserDto registerUserDto);
}
