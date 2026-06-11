package org.samtar.warehouse.vendors.service;

import jakarta.transaction.Transactional;
import org.samtar.warehouse.common.embedded.UserChild;
import org.samtar.warehouse.common.enums.LocationTree;
import org.samtar.warehouse.common.enums.Roles;
import org.samtar.warehouse.common.exceptions.GenericException;
import org.samtar.warehouse.common.exceptions.LocationException;
import org.samtar.warehouse.location.entity.CityEntity;
import org.samtar.warehouse.location.repository.CityRepository;
import org.samtar.warehouse.vendors.dto.req.CreateVendorReqDto;
import org.samtar.warehouse.vendors.dto.res.VendorRespDto;
import org.samtar.warehouse.vendors.entity.VendorEntity;
import org.samtar.warehouse.vendors.repository.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final CityRepository cityRepository;
    private final PasswordEncoder passwordEncoder;

    public VendorService(VendorRepository vendorRepository, CityRepository cityRepository, PasswordEncoder passwordEncoder) {
        this.vendorRepository = vendorRepository;
        this.cityRepository = cityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public VendorRespDto register(CreateVendorReqDto payload) {
        VendorEntity vendor = new VendorEntity();
        vendor.setUsername(payload.username());
        vendor.setEmail(payload.email());
        vendor.setPassword(passwordEncoder.encode(payload.password()));
        vendor.setPhone(payload.phone());
        vendor.setRole(Roles.VENDOR);
        if (payload.cityID() != null) {
            CityEntity city = cityRepository.findByCityId(payload.cityID())
                    .orElseThrow(() -> LocationException.notExists(LocationTree.CITY, payload.cityID()));
            vendor.setCity(city);
        }
        vendor.setUserChild(new     UserChild(payload.heightInInch(), payload.bodyWeight(), payload.haveDisease()));
        return toResponse(vendorRepository.save(vendor));
    }

    public VendorRespDto getById(Long id) {
        VendorEntity vendor = vendorRepository.findById(id)
                .orElseThrow(() -> GenericException.exceptionGeneric("Vendor not found with id " + id, null, HttpStatus.NOT_FOUND));
        return toResponse(vendor);
    }

    public List<VendorRespDto> getAll() {
        return vendorRepository.findAll().stream().map(this::toResponse).toList();
    }

    private VendorRespDto toResponse(VendorEntity vendor) {
        UserChild child = vendor.getUserChild();
        return new VendorRespDto(
                vendor.getId(),
                vendor.getUsername(),
                vendor.getEmail(),
                vendor.getPhone(),
                vendor.getRole(),
                child.getHeightInInch(),
                child.getBodyWeight(),
                child.getHaveDisease()
        );
    }
}
