package org.samtar.warehouse.inventory.controller;

import jakarta.validation.Valid;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.inventory.dto.req.CreateInventoryReqDto;
import org.samtar.warehouse.inventory.dto.res.InventoryRespDto;
import org.samtar.warehouse.inventory.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<GenericResponseDto<String>> createAnotherInventory(@Valid @RequestBody CreateInventoryReqDto payload) {
        inventoryService.createInventory(payload);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto<>("Another location Added for your product", true, "Inventory Created"));
    }

    @PatchMapping("/update/{inventoryId}")
    public ResponseEntity<GenericResponseDto<String>> updateInventory(@Valid @RequestBody CreateInventoryReqDto payload, @PathVariable Long inventoryId) {
        inventoryService.updateInventory(payload, inventoryId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto<>("Inventory updated for your product", true, "Inventory Updated"));
    }

    @GetMapping("/all/{productId}")
    public ResponseEntity<GenericResponseDto<InventoryRespDto>> updateInventory(@PathVariable(required = true) Long productId) {
        GenericResponseDto<InventoryRespDto> response = new GenericResponseDto<>("Inventories successfully fetched for the product", true, inventoryService.getInventoryByProduct(productId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{inventoryID}/{productId}")
    public ResponseEntity<GenericResponseDto<InventoryRespDto>> updateInventory(@PathVariable(required = true) Long productId, @PathVariable(required = true) Long inventoryID) {
        inventoryService.deleteInventory(inventoryID, productId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto<>("Inventory removed from selected location", true, null));
    }

}
