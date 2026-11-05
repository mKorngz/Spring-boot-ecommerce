package org.example.e_commerce_api.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.e_commerce_api.Constant.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String order_number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(
            mappedBy = "orders",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Order_items> orderItems;

    private Double total_amount;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //triggered by Hibernate right before the SQL INSERT query execute
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }


}
