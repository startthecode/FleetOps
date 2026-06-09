package org.samtar.warehouse.products.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.samtar.warehouse.inventory.entity.InventoryEntity;
import org.samtar.warehouse.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_generator", allocationSize = 10)
    Long productId;

    @NotBlank(message = "Product name can not be blank")
    @Size(max = 180, min = 5, message = "Product name must be between 5 to 180 character")
    @Column(name = "product_name", nullable = false, unique = true, length = 180)
    String productName;

    @NotBlank(message = "Product description can not be blank")
    @Size(max = 280, min = 5, message = "Product description must be between 5 to 280 character")
    @Column(name = "description", nullable = false, length = 280)
    String description;

    @NotNull(message = "Product Price can not be blank")
    @Column(name = "price", nullable = false)
    Double price;

    @NotNull(message = "Product cost can not be blank")
    @Column(name = "cost", nullable = false)
    Double cost;

    @NotNull(message = "Product quantity can not be blank")
    @Column(name = "stock_quantity", nullable = false)
    int stockQuantity;

    @NotNull(message = "Product unit can not be blank")
    @Column(name = "unit", nullable = false)
    int unit;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    UserEntity createdBy;

    @OneToMany(mappedBy = "inventories",fetch = FetchType.LAZY)
    Set<InventoryEntity> inventories= new HashSet<>();

}
