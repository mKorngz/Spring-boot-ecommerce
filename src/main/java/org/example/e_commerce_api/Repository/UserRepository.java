package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByEmailAndEnabled(String email, boolean enabled);
}
