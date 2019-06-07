package com.sharepool.server.logic.user;

import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.user.UserRestErrorMessages;
import com.sharepool.server.rest.user.dto.UserCredentialsDto;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.user.dto.UserLoginDto;
import com.sharepool.server.rest.util.PasswordStorage;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRestRequestHandler {

    private final Logger logger;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserRestRequestHandler(Logger logger, UserRepository userRepository, UserMapper userMapper) {
        this.logger = logger;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserCredentialsDto registerUser(UserDto userDto) {
        if (userRepository.findByUserNameOrEmail(userDto.getUserName(), userDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    UserRestErrorMessages.userWithUserNameOrEmailAlreadyExists(userDto.getUserName(), userDto.getEmail()));
        }

        User user = userMapper.userDtoToUser(userDto);

        try {
            String passwordHash = PasswordStorage.createHash(userDto.getPassword());
            user.setPasswordHash(passwordHash);

            String userToken = UUID.randomUUID().toString();
            user.setUserToken(userToken);

            userRepository.save(user);

            return new UserCredentialsDto(userToken, user.getId());
        } catch (PasswordStorage.CannotPerformOperationException e) {
            logger.error("User {} could not be registered", user.getUserName(), e);
            return null;
        }
    }

    public UserCredentialsDto loginUser(UserLoginDto userLoginDto) {
        Optional<User> userOptional = userRepository.findByUserNameOrEmail(
                userLoginDto.getUserNameOrEmail(), userLoginDto.getUserNameOrEmail());

        try {
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!PasswordStorage.verifyPassword(userLoginDto.getPassword(), user.getPasswordHash())) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }

                return new UserCredentialsDto(user.getUserToken(), user.getId());
            }

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    UserRestErrorMessages.noUserFoundForEmail(userLoginDto.getUserNameOrEmail()));
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
            logger.error("User with email {} could not login", userLoginDto.getUserNameOrEmail(), e);
            return null;
        }
    }
}
