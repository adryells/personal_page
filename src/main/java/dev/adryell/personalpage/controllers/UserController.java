package dev.adryell.personalpage.controllers;

import dev.adryell.personalpage.dtos.LoginDTO;
import dev.adryell.personalpage.models.AuthToken;
import dev.adryell.personalpage.models.User;
import dev.adryell.personalpage.repositories.AuthTokenRepository;
import dev.adryell.personalpage.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<UUID> login(@RequestBody @Valid LoginDTO loginData, HttpServletRequest request){
        User user = userRepository.findByEmail(loginData.email());

        if (user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        if (!user.passwordsMatch(loginData.password())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password.");
        }

        AuthToken authToken = new AuthToken();
        authToken.setUser(user);
        authToken.setSourceIPAddress(request.getRemoteAddr());

        List<AuthToken> authTokens = authTokenRepository.findAllActiveByUserId(user.getId());

        for (AuthToken oldAuthToken : authTokens){
            oldAuthToken.setActive(false);
            authTokenRepository.save(oldAuthToken);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(authTokenRepository.save(authToken).getToken());
    }
}
