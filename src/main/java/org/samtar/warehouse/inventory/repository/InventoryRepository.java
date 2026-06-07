package org.samtar.warehouse.inventory.repository;

import org.samtar.warehouse.inventory.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByInventoryId(long inventoryId);
    List<InventoryEntity> findByProduct_ProductId(long productId);
    List<InventoryEntity> findByVendor_Id(long vendorId);
    List<InventoryEntity> findByWarehouse_WarehouseId(long warehouseId);
    Optional<InventoryEntity> findByVendor_IdAndWarehouse_WarehouseIdAndProduct_ProductId(long vendorId, long warehouseId, long productId);
    Boolean existsByInventoryId(long inventoryId);
    Boolean existsByVendor_IdAndWarehouse_WarehouseIdAndProduct_ProductId(long vendorId, long warehouseId, long productId);
}
