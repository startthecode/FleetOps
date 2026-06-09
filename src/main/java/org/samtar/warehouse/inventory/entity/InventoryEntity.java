package org.samtar.warehouse.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.samtar.warehouse.products.entity.ProductEntity;
import org.samtar.warehouse.user.entity.UserEntity;
import org.samtar.warehouse.warehouse.entity.WarehouseEntity;

@Data
@Entity
@Table(name = "inventory",uniqueConstraints = @UniqueConstraint(
        columnNames = {"vendor" ,
                "warehouse" ,
                "product"}
))
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_generator")
    @SequenceGenerator(name = "inventory_id_generator", sequenceName = "inventory_id_generator", allocationSize = 10)
    Long inventoryId;

    @NotNull(message = "Stock can not be blank")
@Min(value = 0, message = "Stock must be at least 0")
    @Max(value = 1000, message = "Stock cannot exceed 1000")
    @Column(name = "stock_vendor", nullable = false)
    Integer stockVendor;

    @NotNull(message = "Stock can not be blank")
    @Min(value = 0, message = "Stock must be at least 0")
    @Max(value = 1000, message = "Stock cannot exceed 1000")
    @Column(name = "stock_warehouse", nullable = false)
    Integer stockWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor",nullable = true)
    UserEntity vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse",nullable = true)
    WarehouseEntity warehouse;

    @NotNull(message = "Product id can not be blank")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product",nullable = false)
    ProductEntity product;

    public InventoryEntity() {
    }

    public InventoryEntity(ProductEntity product, Integer stockVendor, Integer stockWarehouse, UserEntity vendor, WarehouseEntity warehouse) {
        this.product = product;
        this.stockVendor = stockVendor;
        this.stockWarehouse = stockWarehouse;
        this.vendor = vendor;
        this.warehouse = warehouse;
    }
}
