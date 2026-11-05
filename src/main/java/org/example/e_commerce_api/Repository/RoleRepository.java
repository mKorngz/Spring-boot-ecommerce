package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
    Page<Role> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
