package org.example.e_commerce_api.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.e_commerce_api.Constant.PermissionEnum;

@Table(
        name = "permissions",
        indexes = {
                @Index(name = "idx_permission_name", columnList = "name", unique = true),
                @Index(name = "idx_permission_category", columnList = "category"),
                @Index(name = "idx_permission_category_name", columnList = "category, name")
        })
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true, nullable = false, length = 100)
    private PermissionEnum name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "category", length = 100)
    private String category;
}
