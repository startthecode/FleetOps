package org.samtar.warehouse.warehouse.service;

import org.samtar.warehouse.common.enums.LocationTree;
import org.samtar.warehouse.common.exceptions.LocationException;
import org.samtar.warehouse.common.exceptions.WarehouseException;
import org.samtar.warehouse.location.entity.CityEntity;
import org.samtar.warehouse.location.repository.CityRepository;
import org.samtar.warehouse.products.repository.ProductRepository;
import org.samtar.warehouse.warehouse.dto.req.CreateWareHouseReqDto;
import org.samtar.warehouse.warehouse.dto.res.WarehouseResDto;
import org.samtar.warehouse.warehouse.entity.WarehouseEntity;
import org.samtar.warehouse.warehouse.mapper.WarehouseMapper;
import org.samtar.warehouse.warehouse.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WarehouseService {
    ProductRepository productRepository;
    WarehouseRepository warehouseRepository;
    WarehouseMapper warehouseMapper;
    CityRepository cityRepository;

    public WarehouseService(CityRepository cityRepository, ProductRepository productRepository, WarehouseMapper warehouseMapper, WarehouseRepository warehouseRepository) {
        this.cityRepository = cityRepository;
        this.productRepository = productRepository;
        this.warehouseMapper = warehouseMapper;
        this.warehouseRepository = warehouseRepository;
    }

    public WarehouseResDto createWarehouse(CreateWareHouseReqDto payload) {
        throwIfExist(payload.warehouseName());
        CityEntity cityEntity = cityRepository.findByCityId(payload.cityId()).orElseThrow(() -> LocationException.notExists(LocationTree.CITY, payload.cityId()));
        WarehouseEntity newWarehouse = new WarehouseEntity();
        newWarehouse.setCity(cityEntity);
        newWarehouse.setWarehouseName(payload.warehouseName());
        return warehouseMapper.toResponse(warehouseRepository.save(newWarehouse));
    }

    public WarehouseResDto updateWarehouse(CreateWareHouseReqDto payload, Long warehouseId) {
        WarehouseEntity warehouseEntity = warehouseRepository.findByWarehouseId(warehouseId).orElseThrow(() -> WarehouseException.notExists(warehouseId));
        if (!Objects.equals(warehouseEntity.getWarehouseName(), payload.warehouseName())) {
            throwIfExist(payload.warehouseName());
        }
        if (!Objects.equals(warehouseEntity.getCity().getCityId(), payload.cityId())) {
            CityEntity cityEntity = cityRepository.findByCityId(payload.cityId()).orElseThrow(() -> LocationException.notExists(LocationTree.CITY, payload.cityId()));
            warehouseEntity.setCity(cityEntity);
        }
        return warehouseMapper.toResponse(warehouseRepository.save(warehouseEntity));
    }

    public void deleteWarehouse(long warehouseID) {
        throwIfNotExist(warehouseID);
        warehouseRepository.deleteById(warehouseID);
    }

    public List<WarehouseResDto> getAllWarehouses() {
        return warehouseRepository.findAll().stream().map(e -> warehouseMapper.toResponse(e)).toList();
    }

    private void throwIfExist(String warehouseName) {
        if (warehouseRepository.existsByWarehouseNameIgnoreCase(warehouseName))
            throw WarehouseException.alreadyExists(warehouseName);
    }

    private void throwIfExist(Long warehouseId) {
        if (warehouseRepository.existsByWarehouseId(warehouseId)) throw WarehouseException.alreadyExists(warehouseId);

    }

    private void throwIfNotExist(String warehouseName) {
        if (!warehouseRepository.existsByWarehouseNameIgnoreCase(warehouseName))
            throw WarehouseException.notExists(warehouseName);
    }

    private void throwIfNotExist(Long warehouseId) {
        if (!warehouseRepository.existsByWarehouseId(warehouseId)) throw WarehouseException.notExists(warehouseId);

    }

}
