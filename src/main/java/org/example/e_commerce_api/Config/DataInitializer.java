package org.example.e_commerce_api.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.e_commerce_api.Constant.PermissionEnum;
import org.example.e_commerce_api.Entity.Permissions;
import org.example.e_commerce_api.Entity.Role;
import org.example.e_commerce_api.Entity.Users;
import org.example.e_commerce_api.Repository.PermissionRepository;
import org.example.e_commerce_api.Repository.RoleRepository;
import org.example.e_commerce_api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
@Order(1)
public class DataInitializer implements ApplicationRunner {
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    private void seedPermission() {
        try {
            for (var permissionEnum : PermissionEnum.values()) {
                permissionRepository
                        .findByName(permissionEnum)
                        .orElseGet(
                                () ->
                                        permissionRepository.save(
                                                Permissions.builder()
                                                        .name(permissionEnum)
                                                        .description(permissionEnum.getDescription())
                                                        .category(permissionEnum.getCategory())
                                                        .build()));
                log.info("Permission {} has been created", permissionEnum.name());
            }
        } catch (Exception e) {
            log.error("Failed to create permissions", e);
        }
    }

    private void seedRoles() {
        try {
            Set<PermissionEnum> normalUserPermissionEnums = Set.of(
                    PermissionEnum.PRODUCT_VIEW,
                    PermissionEnum.CATEGORY_VIEW,
                    PermissionEnum.CART_VIEW,
                    PermissionEnum.CART_ADD_ITEM,
                    PermissionEnum.CART_UPDATE_ITEM,
                    PermissionEnum.CART_REMOVE_ITEM,
                    PermissionEnum.CART_CLEAR,
                    PermissionEnum.ORDER_CREATE,
                    PermissionEnum.ORDER_VIEW,
                    PermissionEnum.ORDER_CANCEL,
                    PermissionEnum.CUSTOMER_VIEW,
                    PermissionEnum.CUSTOMER_UPDATE);

            Set<Permissions> normalUserPermissions = permissionRepository.findAll().stream()
                    .filter(permissions -> normalUserPermissionEnums.contains(permissions.getName())).collect(Collectors.toSet());
            roleRepository.findByName(ADMIN_ROLE)
                    .ifPresentOrElse(
                            role -> log.info("Role {} already exists", ADMIN_ROLE),
                            () -> {
                                roleRepository.save(
                                        Role.builder()
                                                .name(ADMIN_ROLE)
                                                .isActive(true)
                                                .description("Manage everything")
                                                .permissions(new HashSet<>(permissionRepository.findAll()))
                                                .build());
                                log.info("Role {} created", ADMIN_ROLE);
                            }

                    );



            roleRepository
                    .findByName(USER_ROLE)
                    .ifPresentOrElse(
                            role -> log.info("Role {} already exists", USER_ROLE),
                            () -> {
                                roleRepository.save(
                                        Role.builder()
                                                .name(USER_ROLE)
                                                .permissions(normalUserPermissions)
                                                .isActive(true)
                                                .build());
                                log.info("Role {} created with default user permissions", USER_ROLE);
                            }
                    );

        } catch (Exception e) {
            log.info("Role not created", e);
        }
    }

    private void initUsers() {
        userRepository.findByEmail(adminEmail)
                .ifPresentOrElse(
                        users -> log.info("Admin already exists"),
                        () -> {
                            Role roles = roleRepository.findByName(DataInitializer.ADMIN_ROLE).orElseThrow(
                                    () -> new RuntimeException("ADMIN role not found")

                            );
                            userRepository.save(
                                    Users.builder()
                                            .fullName("Korng")
                                            .email(adminEmail)
                                            .password(passwordEncoder.encode(adminPassword))
                                            .enabled(true)
                                            .roles(Set.of(roles))
                                            .build());
                            log.info("Admin initialized successfully!");
                         });
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedPermission();
        seedRoles();
        initUsers();
    }
}
