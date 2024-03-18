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

    record RoleData(List<String> permissions, String name, String slug, String description) {
    }

    record UserData(String role_slug, String name, String email, String password) {
    }

    @PostConstruct
    public void init() {
        this.createPermissions();
        this.createRoles();
        this.createUsers();
    }

    public void createRoles() {
        List<RoleData> roles_data = getRolesData();

        for (RoleData role_datum : roles_data) {
            Role role = roleRepository.findBySlug(role_datum.slug);

            if (role != null) {
                role.setName(role_datum.name);
                role.setDescription(role_datum.description);
                roleRepository.save(role);
                System.out.println("Role " + role.getName() + " Atualizada.");
            } else {
                Role new_role = new Role();
                new_role.setName(role_datum.name);
                new_role.setSlug(role_datum.slug);
                new_role.setDescription(role_datum.description);
                Set<Permission> permissions = new HashSet<>();

                for (String permission_slug : role_datum.permissions) {
                    Permission permission = permissionRepository.findBySlug(permission_slug);
                    permissions.add(permission);
                }

                new_role.setPermissions(permissions);
                roleRepository.save(new_role);
                System.out.println("Role " + new_role.getName() + " Adicionada.");
            }
        }
    }

    private static List<RoleData> getRolesData() {
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
                new RoleData(admin_permissions, "Admin", "admin", "Can do everything."),
                new RoleData(mod_permissions, "Mod", "mod", "Can do almost anything.")
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        List<UserData> users_data = new ArrayList<>(Arrays.asList(
                new UserData("admin", "Adryell", "adryell@shrimpwave.com", "87654321"),
                new UserData("mod", "Jaw", "jaw@shrimpwave.com", "12345678")
        ));

        for (UserData user_datum : users_data) {
            User user = userRepository.findByEmail(user_datum.email);

            if (user != null) {
                user.setName(user_datum.name);

                user.setPassword(encoder.encode(user_datum.password));
                Role role = roleRepository.findBySlug(user_datum.role_slug);
                user.setRole(role);
                userRepository.save(user);
                System.out.println("Usuário " + user.getName() + " Atualizado.");
            } else {
                user = new User();
                user.setName(user_datum.name);
                user.setEmail(user_datum.email);

                user.setPassword(encoder.encode(user_datum.password));

                Role role = roleRepository.findBySlug(user_datum.role_slug);
                user.setRole(role);
                userRepository.save(user);
                System.out.println("Usuário " + user.getName() + " Adicionado.");
            }
        }
    }
}
