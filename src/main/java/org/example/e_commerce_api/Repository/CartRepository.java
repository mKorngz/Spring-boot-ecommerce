package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {
    Optional<Carts> findByUsersId(Long userId);
}
