package org.samtar.warehouse.cart.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.samtar.warehouse.user.entity.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "cartItems")
@EqualsAndHashCode(exclude = "cartItems")
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_generator")
    @SequenceGenerator(name = "cart_id_generator", sequenceName = "cart_id_generator")
    Long id;

    @OneToOne()
    @JoinColumn(name = "`user`", nullable = false, unique = true)
    private UserEntity user;

    @NotNull(message = "Total amount can not be blank")
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @NotNull(message = "Total Items can not be blank")
    @Column(name = "total_items", nullable = false)
    @Min(value = 1)
    @Max(value = 100)
    private int totalItems;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<CartItemsEntity> cartItems = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CartEntity(Long id, UserEntity user, @NotNull(message = "Total amount can not be blank") double totalAmount,
            @NotNull(message = "Total Items can not be blank") @Min(1) @Max(100) int totalItems,
            Set<CartItemsEntity> cartItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
        this.cartItems = cartItems;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CartEntity() {
    }

}
