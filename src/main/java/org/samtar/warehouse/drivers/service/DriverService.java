package org.samtar.warehouse.drivers.service;

import jakarta.transaction.Transactional;
import org.samtar.warehouse.common.embedded.UserChild;
import org.samtar.warehouse.common.enums.LocationTree;
import org.samtar.warehouse.common.enums.Roles;
import org.samtar.warehouse.common.exceptions.GenericException;
import org.samtar.warehouse.common.exceptions.LocationException;
import org.samtar.warehouse.drivers.dto.req.CreateDriverReqDto;
import org.samtar.warehouse.drivers.dto.res.DriverRespDto;
import org.samtar.warehouse.drivers.entity.DriverEntity;
import org.samtar.warehouse.drivers.repository.DriverRepository;
import org.samtar.warehouse.location.entity.CityEntity;
import org.samtar.warehouse.location.repository.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final CityRepository cityRepository;
    private final PasswordEncoder passwordEncoder;

    public DriverService(DriverRepository driverRepository, CityRepository cityRepository, PasswordEncoder passwordEncoder) {
        this.driverRepository = driverRepository;
        this.cityRepository = cityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public DriverRespDto register(CreateDriverReqDto payload) {
        DriverEntity driver = new DriverEntity();
        driver.setUsername(payload.username());
        driver.setEmail(payload.email());
        driver.setPassword(passwordEncoder.encode(payload.password()));
        driver.setPhone(payload.phone());
        driver.setRole(Roles.DRIVER);
        if (payload.cityID() != null) {
            CityEntity city = cityRepository.findByCityId(payload.cityID())
                    .orElseThrow(() -> LocationException.notExists(LocationTree.CITY, payload.cityID()));
            driver.setCity(city);
        }
        driver.setUserChild(new UserChild(payload.heightInInch(), payload.bodyWeight(), payload.haveDisease()));
        driver.setDriveRating(payload.driveRating());
        driver.setHaveDrivingLicence(payload.haveDrivingLicence());
        return toResponse(driverRepository.save(driver));
    }

    public DriverRespDto getById(Long id) {
        DriverEntity driver = driverRepository.findById(id)
                .orElseThrow(() -> GenericException.exceptionGeneric("Driver not found with id " + id, null, HttpStatus.NOT_FOUND));
        return toResponse(driver);
    }

    public List<DriverRespDto> getAll() {
        return driverRepository.findAll().stream().map(this::toResponse).toList();
    }

    private DriverRespDto toResponse(DriverEntity driver) {
        UserChild child = driver.getUserChild();
        return new DriverRespDto(
                driver.getId(),
                driver.getUsername(),
                driver.getEmail(),
                driver.getPhone(),
                driver.getRole(),
                child.getHeightInInch(),
                child.getBodyWeight(),
                child.getHaveDisease(),
                driver.getDriveRating(),
                driver.getHaveDrivingLicence()
        );
    }
}
