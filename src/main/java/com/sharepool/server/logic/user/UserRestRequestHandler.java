package com.sharepool.server.logic.user;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.UserRestErrorMessages;
import com.sharepool.server.rest.user.dto.LoginUserDto;
import com.sharepool.server.rest.user.dto.RegisterUserDto;
import com.sharepool.server.rest.util.PasswordStorage;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

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
        User user = userMapper.registerUserDtoToAppUser(registerUserDto);

        try {
            String passwordHash = PasswordStorage.createHash(registerUserDto.getPassword());
            user.setPasswordHash(passwordHash);

            String userToken = UUID.randomUUID().toString();
            user.setUserToken(UUID.randomUUID().toString());

            userRepository.save(user);

            return userToken;
        } catch (PasswordStorage.CannotPerformOperationException e) {
            logger.error("User {} could not be registered", user.getUserName(), e);
            return null;
        }
    }

    public String loginUser(LoginUserDto loginUserDto) {
        Optional<User> userOptional = userRepository.findByEmail(loginUserDto.getEmail());

        try {
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!PasswordStorage.verifyPassword(loginUserDto.getPassword(), user.getPasswordHash())) {
                    throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
                }

                return user.getUserToken();
            }

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    UserRestErrorMessages.noUserFoundForEmail(loginUserDto.getEmail()));
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
            logger.error("User with email {} could not login", loginUserDto.getEmail(), e);
            return null;
        }
    }
}
