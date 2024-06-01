package dev.adryell.personalpage.repositories;

import dev.adryell.personalpage.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findBySlug(String slug);
}
