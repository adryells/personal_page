package dev.adryell.personalpage.services;

import dev.adryell.personalpage.models.AuthToken;
import dev.adryell.personalpage.models.User;
import dev.adryell.personalpage.repositories.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthTokenService {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    public User getUserFromToken(UUID token) {
        AuthToken authToken = authTokenRepository.findByToken(token);
        if (authToken != null) {
            return authToken.getUser();
        }
        return null;
    }
}
