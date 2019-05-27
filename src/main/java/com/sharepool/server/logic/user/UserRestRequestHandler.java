package com.sharepool.server.logic.user;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.domain.AppUser;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import com.sharepool.server.rest.util.PasswordStorage;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRestRequestHandler {

    private final Logger logger;
    private final AppUserRepository userRepository;
    private final UserMapper userMapper;

    public UserRestRequestHandler(Logger logger, AppUserRepository userRepository, UserMapper userMapper) {
        this.logger = logger;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String registerUser(RegisterUserDto registerUserDto) {
        AppUser user = userMapper.registerUserDtoToAppUser(registerUserDto);

        try {
            String passwordHash = PasswordStorage.createHash(registerUserDto.getPassword());
            user.setPasswordHash(passwordHash);

            String userToken = UUID.randomUUID().toString();
            String userTokenHash = PasswordStorage.createHash(userToken);
            user.setUserTokenHash(userTokenHash);

            userRepository.save(user);

            return userToken;
        } catch (PasswordStorage.CannotPerformOperationException e) {
            logger.error("User {} could not be registered", user.getUsername(), e);
            return null;
        }
    }

    public String loginUser(LoginUserDto loginUserDto) {
        Optional<AppUser> userOptional = userRepository.findByEmail(loginUserDto.getEmail());

        try {
            if (userOptional.isPresent()) {
                AppUser user = userOptional.get();
                if (!PasswordStorage.verifyPassword(loginUserDto.getPassword(), user.getPasswordHash())) {
                    throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
                }

                return "iu1h23ui";
            }
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
            logger.error("User {} could not login", user.getUsername(), e);
            return null;
        }

        return null;
    }
}
