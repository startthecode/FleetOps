package org.samtar.warehouse.warehouse.repository;

import org.samtar.warehouse.warehouse.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
    Optional<WarehouseEntity> findByWarehouseNameIgnoreCase(String warehouseName);
    Optional<WarehouseEntity> findByWarehouseId(long warehouseId);
    List<WarehouseEntity> findByCity_cityId(Long cityId);
    Boolean existsByWarehouseNameIgnoreCase(String warehouseName);
    Boolean existsByWarehouseId(long warehouseId);
}
