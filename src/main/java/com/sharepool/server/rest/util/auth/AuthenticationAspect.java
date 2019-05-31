package com.sharepool.server.rest.util.auth;

import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@Aspect
public class AuthenticationAspect {

    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final UserContext userContext;

    public AuthenticationAspect(
            HttpServletRequest request,
            UserRepository userRepository,
            UserContext userContext
    ) {
        this.request = request;
        this.userRepository = userRepository;
        this.userContext = userContext;
    }

    @Before("execution(public * *(..)) " +
            "&& within(@org.springframework.web.bind.annotation.RestController *) " +
            "&& !within(com.sharepool.server.rest.user.UserResource)")
    public boolean authenticate() {
        String token = request.getHeader("Auth-Token");
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No 'Auth-Token'-Header set.");
        }

        Optional<User> user = userRepository.findByUserToken(token);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication!");
        }

        userContext.setUserToken(() -> token);
        userContext.setUser(() - user.get());

        return true;
    }
}
