package org.samtar.warehouse.tracking.entity;

import java.time.LocalDateTime;

import org.samtar.warehouse.common.enums.TrackingStatus;
import org.samtar.warehouse.orders.entity.OrderEntity;
import org.samtar.warehouse.products.entity.ProductEntity;
import org.samtar.warehouse.warehouse.entity.WarehouseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.criteria.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = { "vendor", "wareHouse", "orderinfo" })
@EqualsAndHashCode(exclude = { "vendor", "wareHouse", "orderinfo" })
@Table(name = "order_track", uniqueConstraints = @UniqueConstraint(columnNames = { "vendor", "wareHouse",
        "orderinfo" }))
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "track_id_generator")
    @SequenceGenerator(name = "track_id_generator", sequenceName = "track_id_generator", allocationSize = 10)
    Long id;

    @NotNull(message = "Step can not be blank")
    @Column(name = "step", nullable = false)
    Integer step;

    @ManyToOne()
    @JoinColumn(name = "ware_house", nullable = true)
    WarehouseEntity wareHouse;

    @ManyToOne()
    @JoinColumn(name = "vendor", nullable = true)
    WarehouseEntity vendor;

    @NotNull(message = "Status can not be blank")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TrackingStatus status;

    @ManyToOne
    @JoinColumn(nullable = false)
    OrderEntity orderinfo;

    @ManyToOne
    @JoinColumn(nullable = false)
    ProductEntity product;

    @Column(nullable = true)
    LocalDateTime reachedTime;

    public TrackEntity(Long id, @NotNull(message = "Step can not be blank") Integer step, WarehouseEntity wareHouse,
            WarehouseEntity vendor, @NotNull(message = "Status can not be blank") TrackingStatus status,
            OrderEntity orderinfo, LocalDateTime reachedTime) {
        this.id = id;
        this.step = step;
        this.wareHouse = wareHouse;
        this.vendor = vendor;
        this.status = status;
        this.orderinfo = orderinfo;
        this.reachedTime = reachedTime;
    }

    public TrackEntity() {
    }

    

}
