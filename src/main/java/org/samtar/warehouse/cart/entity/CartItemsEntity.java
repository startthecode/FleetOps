package org.samtar.warehouse.cart.entity;

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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "cart")
@EqualsAndHashCode(exclude = "cart")
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = { "product", "cart" }))
public class CartItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_items_id_generator")
    @SequenceGenerator(name = "cart_items_id_generator", sequenceName = "cart_items_id_generator")
    Long id;

    @ManyToOne()
    @JoinColumn(name = "product", nullable = false)
    ProductEntity product;

    @ManyToOne()
    @JoinColumn(name = "cart", nullable = false)
    CartEntity cart;

    @NotNull(message = "Product Quantity can not be blank")
    @Column(name = "quantity", nullable = false)
    @Max(100)
    @Min(1)
    int quantity;

    @NotNull(message = "Price can not be blank")
    @Column(name = "price", nullable = false)
    @Min(0)
    Double price;

    public CartItemsEntity(
            ProductEntity product,
            CartEntity cart,
            @NotNull(message = "Product Quantity can not be blank") @Max(100) @Min(1) int quantity,
            @NotNull(message = "Price can not be blank") @Min(0) Double price) {
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
        this.price = price;
    }

    public CartItemsEntity() {
    }

}
