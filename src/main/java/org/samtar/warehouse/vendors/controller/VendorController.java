package org.samtar.warehouse.vendors.controller;

import jakarta.validation.Valid;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.vendors.dto.req.CreateVendorReqDto;
import org.samtar.warehouse.vendors.dto.res.VendorRespDto;
import org.samtar.warehouse.vendors.service.VendorService;
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
@RequestMapping("/api/v1/vendor")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponseDto<VendorRespDto>> register(@Valid @RequestBody CreateVendorReqDto payload) {
        VendorRespDto data = vendorService.register(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponseDto<>("Vendor created", true, data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponseDto<VendorRespDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new GenericResponseDto<>("Vendor fetched", true, vendorService.getById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponseDto<List<VendorRespDto>>> getAll() {
        return ResponseEntity.ok(new GenericResponseDto<>("Vendors fetched", true, vendorService.getAll()));
    }
}
