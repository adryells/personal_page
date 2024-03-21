package dev.adryell.personalpage.services;

import dev.adryell.personalpage.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public boolean roleHasPermission(Long roleId, String permissionSlug) {
        return roleRepository.existsByPermissionSlugAndRoleId(permissionSlug, roleId);
    }
}
