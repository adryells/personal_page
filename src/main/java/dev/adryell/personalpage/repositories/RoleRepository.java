package dev.adryell.personalpage.repositories;

import dev.adryell.personalpage.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findBySlug(String slug);
}