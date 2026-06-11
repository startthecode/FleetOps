package org.samtar.warehouse.drivers.controller;

import jakarta.validation.Valid;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.drivers.dto.req.CreateDriverReqDto;
import org.samtar.warehouse.drivers.dto.res.DriverRespDto;
import org.samtar.warehouse.drivers.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponseDto<DriverRespDto>> register(@Valid @RequestBody CreateDriverReqDto payload) {
        DriverRespDto data = driverService.register(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponseDto<>("Driver created", true, data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponseDto<DriverRespDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new GenericResponseDto<>("Driver fetched", true, driverService.getById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponseDto<List<DriverRespDto>>> getAll() {
        return ResponseEntity.ok(new GenericResponseDto<>("Drivers fetched", true, driverService.getAll()));
    }
}
