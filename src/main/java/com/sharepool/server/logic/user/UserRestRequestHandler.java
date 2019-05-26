package com.sharepool.server.logic.user;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.domain.AppUser;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRestRequestHandler {

    private final AppUserRepository userRepository;
    private final UserMapper userMapper;

    public UserRestRequestHandler(AppUserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String registerUser(RegisterUserDto registerUserDto) {
        // TODO: hash password in "registerUserDto"
        userRepository.save(userMapper.registerUserDtoToAppUser(registerUserDto));

        // TODO: generate token
        return "a8s7d9as";
    }

    public String loginUser(LoginUserDto loginUserDto) {
        Optional<AppUser> userOptional = userRepository.findByEmail(loginUserDto.getEmail());

        if (userOptional.isPresent()) {
            // TODO: hash password in "loginUserDto"
            String passwordHash = loginUserDto.getPassword();
            if (userOptional.get().getPasswordHash().equals(passwordHash)) {
                // TODO: generate token
                return "iu1h23ui";
            }
        }

        return null;
    }
}
