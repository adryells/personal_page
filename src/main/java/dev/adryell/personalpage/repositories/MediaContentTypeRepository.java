package dev.adryell.personalpage.repositories;

import dev.adryell.personalpage.models.MediaContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaContentTypeRepository extends JpaRepository<MediaContentType, Long> {
    Optional<MediaContentType> findBySlug(String slug);
}
