package dev.adryell.personalpage.services;

import dev.adryell.personalpage.exceptions.AuthenticationException;
import dev.adryell.personalpage.models.User;
import dev.adryell.personalpage.utils.enums.Permissions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class AuthTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private RoleService roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = extractTokenFromRequest(request);
        if (token == null) {
            throw new AuthenticationException("Unauthorized. Token not provided.", HttpStatus.UNAUTHORIZED);
        }

        token = token.replace("Bearer ", "");

        if (!isValidUUID(token)){
            throw new AuthenticationException("Unauthorized. Invalid token format.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = authTokenService.getUserFromToken(UUID.fromString(token));
        if (user == null) {
            throw new AuthenticationException("Unauthorized. Invalid token.", HttpStatus.UNAUTHORIZED);
        }

        Permissions requiredPermission = determineRequiredPermission(handler);
        if (!roleService.roleHasPermission(user.getRole().getId(), requiredPermission.name().toLowerCase())) {
            throw new AuthenticationException("Access denied. You don't have permission to access this resource.", HttpStatus.FORBIDDEN);
        }

        return true;
    }

    private boolean isValidUUID(String token) {
        try {
            UUID.fromString(token);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private Permissions determineRequiredPermission(Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            RequiresPermission annotation = handlerMethod.getMethodAnnotation(RequiresPermission.class);

            if (annotation != null) {
                return annotation.value()[0];
            }
        }

        return null;
    }
}
