package dev.adryell.personalpage.data;

import dev.adryell.personalpage.models.Permission;
import dev.adryell.personalpage.models.Role;
import dev.adryell.personalpage.models.User;
import dev.adryell.personalpage.repositories.PermissionRepository;
import dev.adryell.personalpage.repositories.RoleRepository;
import dev.adryell.personalpage.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        this.createPermissions();
        this.createRoles();
        this.createUsers();
    }

    public void createRoles() {
        List<Map<String, Object>> roles_data = getRoleMaps();

        for (Map<String, Object> role_datum : roles_data) {
            Role role = roleRepository.findBySlug((String) role_datum.get("slug"));

            if (role != null) {
                role.setName((String) role_datum.get("name"));
                role.setDescription((String) role_datum.get("description"));
                roleRepository.save(role);
                System.out.println("Role " + role.getName() + " Atualizada.");
            } else {
                Role new_role = new Role();
                new_role.setName((String) role_datum.get("name"));
                new_role.setSlug((String) role_datum.get("slug"));
                new_role.setDescription((String) role_datum.get("description"));
                Set<Permission> permissions = new HashSet<>();

                for (Object permission_slug : (List<?>) role_datum.get("permissions")) {
                    if (permission_slug instanceof String) {
                        Permission permission = permissionRepository.findBySlug((String) permission_slug);
                        permissions.add(permission);
                    }
                }
                new_role.setPermissions(permissions);
                roleRepository.save(new_role);
                System.out.println("Role " + new_role.getName() + " Adicionada.");
            }
        }
    }

    private static List<Map<String, Object>> getRoleMaps() {
        List<String> admin_permissions = Arrays.asList(
                "create_post", "update_post", "delete_post", "get_post",
                "create_project", "update_project", "delete_project", "get_project",
                "create_tag", "update_tag", "delete_tag", "get_tag",
                "create_user", "update_user", "delete_user", "get_user"
        );
        List<String> mod_permissions = Arrays.asList(
                "create_post", "update_post", "delete_post", "get_post",
                "create_project", "update_project", "delete_project", "get_project",
                "create_tag", "update_tag", "delete_tag", "get_tag",
                "create_user", "update_user", "delete_user", "get_user"
        );

        return new ArrayList<>(Arrays.asList(
                Map.of(
                        "permissions", admin_permissions,
                        "name", "Admin",
                        "slug", "admin",
                        "description", "Can do everything."
                ),
                Map.of(
                        "permissions", mod_permissions,
                        "name", "Mod",
                        "slug", "mod",
                        "description", "Can do almost anything."
                )
        ));
    }

    public void createPermissions() {
        String[] permission_names = {
                "Create Post", "Update Post", "Delete Post", "Get Post",
                "Create Project", "Update Project", "Delete Project", "Get Project",
                "Create Tag", "Update Tag", "Delete Tag", "Get Tag",
                "Create User", "Update User", "Delete User", "Get User"
        };

        for (String permission_name : permission_names) {
            String slug = permission_name.replace(" ", "_").toLowerCase();
            Permission permission = permissionRepository.findBySlug(slug);

            if (permission != null) {
                permission.setName(permission_name);
                permission.setDescription(String.format("Allows %s.", permission_name));
                permissionRepository.save(permission);
                System.out.println("Permissão " + permission_name + " Atualizada.");
            } else {
                Permission new_permission = new Permission();
                new_permission.setName(permission_name);
                new_permission.setSlug(slug);
                new_permission.setDescription(String.format("Allows %s.", permission_name));
                permissionRepository.save(new_permission);
                System.out.println("Permissão " + permission_name + " Adicionada.");
            }
        }
    }

    public void createUsers() {
        List<Map<String, String>> users_data = new ArrayList<>(Arrays.asList(
                Map.of(
                        "role_slug", "admin",
                        "name", "Adryell",
                        "email", "adryell@shrimpwave.com",
                        "password", "87654321"
                ),
                Map.of(
                        "role_slug", "mod",
                        "name", "Jaw",
                        "email", "jaw@shrimpwave.com",
                        "password", "12345678"
                )
        ));

        for (Map<String, String> user_datum : users_data) {
            User user = userRepository.findByEmail(user_datum.get("email"));

            if (user != null) {
                user.setName(user_datum.get("name"));

                user.setPassword(user_datum.get("password"));
                Role role = roleRepository.findBySlug(user_datum.get("role_slug"));
                user.setRole(role);
                userRepository.save(user);
                System.out.println("Usuário " + user.getName() + " Atualizado.");
            } else {
                user = new User();
                user.setName(user_datum.get("name"));
                user.setEmail(user_datum.get("email"));

                user.setPassword(user_datum.get("password"));

                Role role = roleRepository.findBySlug(user_datum.get("role_slug"));
                user.setRole(role);
                userRepository.save(user);
                System.out.println("Usuário " + user.getName() + " Adicionado.");
            }
        }
    }
}
