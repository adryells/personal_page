package dev.adryell.personalpage.services;

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
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized. Token not provided.");
            return false;
        }

        token = token.replace("Bearer ", "");

        if (!isValidUUID(token)){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Unauthorized. Invalid token format.");
            return false;
        }

        User user = authTokenService.getUserFromToken(UUID.fromString(token));
        if (user == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized. Invalid token.");
            return false;
        }

        Permissions requiredPermission = determineRequiredPermission(handler);
        if (!roleService.roleHasPermission(user.getRole().getId(), requiredPermission.name().toLowerCase())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access denied. You don't have permission to access this resource.");
            return false;
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
