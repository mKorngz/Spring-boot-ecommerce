package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Constant.PermissionEnum;
import org.example.e_commerce_api.Entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, Long> {
    Optional<Permissions> findByName(PermissionEnum permissionEnum);
    List<Permissions> findByNameIn(Collection<PermissionEnum> names);
}
