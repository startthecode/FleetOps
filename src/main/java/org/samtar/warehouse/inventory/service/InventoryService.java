package org.samtar.warehouse.inventory.service;

import jakarta.transaction.Transactional;
import org.samtar.warehouse.common.exceptions.InventoryException;
import org.samtar.warehouse.inventory.dto.req.CreateInventoryReqDto;
import org.samtar.warehouse.inventory.dto.res.InventoriesListDto;
import org.samtar.warehouse.inventory.dto.res.InventoryItemDto;
import org.samtar.warehouse.inventory.dto.res.InventoryRespDto;
import org.samtar.warehouse.inventory.entity.InventoryEntity;
import org.samtar.warehouse.inventory.repository.InventoryRepository;
import org.samtar.warehouse.products.repository.ProductRepository;
import org.samtar.warehouse.shared.services.UserSharedService;
import org.samtar.warehouse.user.repository.UserRepository;
import org.samtar.warehouse.warehouse.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    UserRepository userRepository;
    InventoryRepository inventoryRepository;
    WarehouseRepository warehouseRepository;
    ProductRepository productRepository;
    UserSharedService userSharedService;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository, UserRepository userRepository, UserSharedService userSharedService, WarehouseRepository warehouseRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userSharedService = userSharedService;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public boolean createInventory(CreateInventoryReqDto payload){
        InventoryEntity newInventory = new InventoryEntity();
        newInventory.setProduct(productRepository.getReferenceById(payload.productId()));
        newInventory.setVendor(Boolean.TRUE.equals(payload.selfStorage()) ? userSharedService.getCurrentUser() : null);
        newInventory.setWarehouse(payload.warehouseId() != null ? warehouseRepository.getReferenceById(payload.warehouseId()) : null);
        newInventory.setStockVendor(payload.stock_vendor());
        newInventory.setStockWarehouse(payload.stock_warehouse());
        return  inventoryRepository.save(newInventory).getInventoryId() != null;
    }

    @Transactional
    public void updateInventory(CreateInventoryReqDto payload,Long inventoryID){
        InventoryEntity inventory = inventoryRepository.findByInventoryId(inventoryID).orElseThrow(()-> InventoryException.notExists(inventoryID));
        inventory.setVendor(Boolean.TRUE.equals(payload.selfStorage()) ? userSharedService.getCurrentUser() : null);
        inventory.setWarehouse(payload.warehouseId() != null ? warehouseRepository.getReferenceById(payload.warehouseId()) : null);
        inventory.setStockVendor(payload.stock_vendor());
        inventory.setStockWarehouse(payload.stock_warehouse());
        inventoryRepository.save(inventory);
    }
    public InventoryRespDto getInventoryByProduct(Long productID){
        List<InventoryEntity> inventories = inventoryRepository.findByProduct_ProductId(productID);
        List<InventoryItemDto> vendorItems = inventories.stream().filter(e->e.getVendor() != null).map(e-> new InventoryItemDto(e.getVendor().getId(),e.getVendor().getUsername(),e.getStockVendor())).toList();
        InventoriesListDto vendorData = new InventoriesListDto("Vendors",vendorItems);
        List<InventoryItemDto> warehouseItems = inventories.stream().filter(e->e.getWarehouse() != null).map(e-> new InventoryItemDto(e.getWarehouse().getWarehouseId(),e.getWarehouse().getWarehouseName(),e.getStockWarehouse())).toList();
        InventoriesListDto warehouseData = new InventoriesListDto("Warehouse",warehouseItems);
        return new InventoryRespDto(inventories.getFirst().getProduct().getProductName(),List.of(warehouseData,vendorData));

    }
    public void deleteInventory(Long inventoryId,Long productID){
        List<InventoryEntity> inventories = inventoryRepository.findByProduct_ProductId(productID);
        if(inventories.size() == 1) throw InventoryException.lastItemDeleteNotAllowed();
        if(!inventoryRepository.existsByInventoryId(inventoryId)) throw  InventoryException.notExists(inventoryId);
        inventoryRepository.deleteById(inventoryId);
    }
}
