package dev.adryell.personalpage.repositories;

import dev.adryell.personalpage.models.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    @Query("SELECT authToken FROM AuthToken authToken WHERE authToken.user.id = ?1 AND authToken.active = true")
    List<AuthToken> findAllActiveByUserId(UUID userId);
}
