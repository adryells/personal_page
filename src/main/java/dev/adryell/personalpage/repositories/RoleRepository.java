package dev.adryell.personalpage.repositories;

import dev.adryell.personalpage.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findBySlug(String slug);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Role r JOIN r.permissions p WHERE p.slug = :permissionSlug AND r.id = :roleId")
    boolean existsByPermissionSlugAndRoleId(String permissionSlug, Long roleId);
}