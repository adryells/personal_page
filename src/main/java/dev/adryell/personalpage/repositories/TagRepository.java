package dev.adryell.personalpage.repositories;

import dev.adryell.personalpage.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findBySlug(String slug);

    Page<Tag> findByActive(Boolean active, Pageable pageable);
}
