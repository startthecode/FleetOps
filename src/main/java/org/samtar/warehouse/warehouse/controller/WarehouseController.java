package org.samtar.warehouse.warehouse.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.warehouse.dto.req.CreateWareHouseReqDto;
import org.samtar.warehouse.warehouse.dto.res.WarehouseResDto;
import org.samtar.warehouse.warehouse.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {
    WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/add")
    public ResponseEntity<GenericResponseDto<WarehouseResDto>> createWarehouse(@Valid @RequestBody CreateWareHouseReqDto payload){
        GenericResponseDto<WarehouseResDto> response = new GenericResponseDto<>("Warehouse added successfully",true,warehouseService.createWarehouse(payload));
    return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/update/{warehouseID}")
    public ResponseEntity<GenericResponseDto<WarehouseResDto>> updateWarehouse(@Valid @RequestBody CreateWareHouseReqDto payload,@PathVariable Long warehouseID){
        GenericResponseDto<WarehouseResDto> response = new GenericResponseDto<>("Warehouse updated successfully",true,warehouseService.updateWarehouse(payload,warehouseID));
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete/{warehouseID}")
    public ResponseEntity<GenericResponseDto<Null>> deleteWarehouse(@PathVariable Long warehouseID){
        warehouseService.deleteWarehouse(warehouseID);
        GenericResponseDto<Null> response = new GenericResponseDto<>("Warehouse deleted successfully",true,null);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponseDto<List<WarehouseResDto>>> getAllWarehouse(@RequestParam(required = false) Long city){
        GenericResponseDto<List<WarehouseResDto>> response = new GenericResponseDto<>("Warehouse fetched successfully",true,warehouseService.getAllWarehouses(city));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
