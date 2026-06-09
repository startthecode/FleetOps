package org.samtar.warehouse.orders.entity;

import java.util.HashSet;
import java.util.Set;

import org.samtar.warehouse.common.enums.OrderStatus;
import org.samtar.warehouse.location.entity.StateEntity;
import org.samtar.warehouse.user.entity.UserEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "orderItems")
@EqualsAndHashCode(exclude = "orderItems")
@Table(name = "`order`")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    @SequenceGenerator(name = "order_id_generator", sequenceName = "order_id_generator", allocationSize = 10)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    UserEntity owner;

    @NotNull(message = "Amount can not be null")
    @Column(name = "total_amount",nullable = false)
    Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<OrderItemsEntity> orderItems = new HashSet<>();

    @ManyToOne()
    @JoinColumn(nullable = false, name = "user_state")
    StateEntity userState;

    public OrderEntity(UserEntity owner, @NotNull(message = "Amount can not be null") Double totalAmount,
            OrderStatus status, Set<OrderItemsEntity> orderItems) {
        this.owner = owner;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = orderItems;
    }

    public OrderEntity() {
    }

    

}
