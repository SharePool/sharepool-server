package com.sharepool.server.rest.util.auth;

import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
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

    @Pointcut("execution(public * *(..)) ")
    public void publicMethods() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllers() {
    }

    @Pointcut("within(com.sharepool.server.rest.user.UserResource)")
    public void userResource() {
    }

    @Pointcut("execution(* com.sharepool.server.rest.user.UserResource.getUserInfo())")
    public void exceptions() {
    }

    @Pointcut("execution(* com.sharepool.server.rest.user.UserResource.getUserId())")
    public void analyticsAuthentication() {
    }

    @Before("publicMethods() && restControllers() && (!userResource() || exceptions())")
    public boolean authenticate() {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    String.format("No %s-Header set.", HttpHeaders.AUTHORIZATION));
        }

        Optional<User> user = userRepository.findByUserToken(token);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials!");
        }

        userContext.setUserToken(token);
        userContext.setUser(user.get());

        return true;
    }

    @Before("analyticsAuthentication()")
    public boolean authenticateAnalytics() {
        String ipAddress = request.getRemoteAddr();

        // check if request comes from localhost
        if (ipAddress.equals(request.getLocalAddr())) {
            return authenticate();
        } else {
            // throw a 404 to keep this endpoint secret
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
