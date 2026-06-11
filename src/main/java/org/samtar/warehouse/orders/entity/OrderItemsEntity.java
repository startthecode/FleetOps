package org.samtar.warehouse.orders.entity;

import org.samtar.warehouse.products.entity.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"userOrder","product"})
@EqualsAndHashCode(exclude = {"userOrder","product"})
@Table(name = "order_items",uniqueConstraints = @UniqueConstraint(columnNames = {"user_order", "product"}))
public class OrderItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_generator")
    @SequenceGenerator(name = "order_item_id_generator", sequenceName = "order_item_id_generator", allocationSize = 50)
    Long id;

    @ManyToOne()
    @JoinColumn(name = "product", nullable = false)
    ProductEntity product;
    
    @NotNull(message = "Product Quantity can not be blank")
    @Column(name = "quantity", nullable = false)
    @Max(100)
    @Min(1)
    int quantity;
    
    @NotNull(message = "Price can not be blank")
    @Column(name = "price", nullable = false)
    @Min(0)
    Double price;
    
    @ManyToOne()
    @JoinColumn(name = "user_order", nullable = false)
    OrderEntity userOrder;

    public OrderItemsEntity(ProductEntity product,
            @NotNull(message = "Product Quantity can not be blank") @Max(100) @Min(1) int quantity,
            @NotNull(message = "Price can not be blank") @Min(0) Double price, OrderEntity userOrder) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.userOrder = userOrder;
    }

}
