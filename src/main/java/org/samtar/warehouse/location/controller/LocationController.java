package org.samtar.warehouse.location.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.common.enums.LocationTree;
import org.samtar.warehouse.location.dto.req.CreateCityReqDto;
import org.samtar.warehouse.location.dto.req.CreateCountryReqDto;
import org.samtar.warehouse.location.dto.req.CreateStateReqDto;
import org.samtar.warehouse.location.dto.req.UpdateReqDto;
import org.samtar.warehouse.location.dto.res.CityResponseDto;
import org.samtar.warehouse.location.dto.res.LocationResDto;
import org.samtar.warehouse.location.dto.res.StateResponseDto;
import org.samtar.warehouse.location.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

//    Country
    @PostMapping("/country/add")
    public ResponseEntity<GenericResponseDto<LocationResDto>> createCountry(@Valid @RequestBody CreateCountryReqDto payload) {
        GenericResponseDto<LocationResDto> response = new GenericResponseDto<>("Country Created successfully", true, locationService.createCountry(payload));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/country/all")
    public ResponseEntity<GenericResponseDto<List<LocationResDto>>> getAllCountry() {
        GenericResponseDto<List<LocationResDto>> response = new GenericResponseDto<>("All countries fetched", true, locationService.getAllCountries());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/country/update")
    public ResponseEntity<GenericResponseDto<LocationResDto>> updateCountry(@Valid @RequestBody UpdateReqDto payload) {
        GenericResponseDto<LocationResDto> response = new GenericResponseDto<>("Country Name update", true, locationService.updateCountry(payload));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/country/delete/{countryId}")
    public ResponseEntity<GenericResponseDto<Null>> deleteCountry(@Valid @PathVariable long countryId) {
        locationService.deleteLocation(LocationTree.COUNTRY,countryId);
        GenericResponseDto<Null> response = new GenericResponseDto<>("Country Deleted with id :" + countryId, true, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    // State
    @PostMapping("/state/add")
    public ResponseEntity<GenericResponseDto<LocationResDto>> createState(@Valid @RequestBody CreateStateReqDto payload) {
        GenericResponseDto<LocationResDto> response = new GenericResponseDto<>("State Created successfully", true, locationService.createState(payload));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/state/{countryId}")
    public ResponseEntity<GenericResponseDto<StateResponseDto>> getStatesByCountry(@Valid @PathVariable long countryId) {
        GenericResponseDto<StateResponseDto> response = new GenericResponseDto<>("State fetched successfully", true, locationService.getStateByCountry(countryId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/state/update")
    public ResponseEntity<GenericResponseDto<LocationResDto>> updateState(@Valid @RequestBody UpdateReqDto payload) {
        GenericResponseDto<LocationResDto> response = new GenericResponseDto<>("State Name update", true, locationService.updateState(payload));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/state/delete/{stateId}")
    public ResponseEntity<GenericResponseDto<Null>> deleteState(@Valid @PathVariable long stateId) {
        locationService.deleteLocation(LocationTree.STATE,stateId);
        GenericResponseDto<Null> response = new GenericResponseDto<>("State Deleted with id :" + stateId, true, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    // CITY
    @PostMapping("/city/add")
    public ResponseEntity<GenericResponseDto<LocationResDto>> createCity(@Valid @RequestBody CreateCityReqDto payload) {
        GenericResponseDto<LocationResDto> response = new GenericResponseDto<>("Country Created successfully", true, locationService.createCity(payload));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/city/{stateID}")
    public ResponseEntity<GenericResponseDto<CityResponseDto>> getCityByState(@Valid @PathVariable long stateID) {
        GenericResponseDto<CityResponseDto> response = new GenericResponseDto<>("State fetched successfully", true, locationService.getCityByState(stateID));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/city/update")
    public ResponseEntity<GenericResponseDto<LocationResDto>> updateCity(@Valid @RequestBody UpdateReqDto payload) {
        GenericResponseDto<LocationResDto> response = new GenericResponseDto<>("City Name update", true, locationService.updateCity(payload));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/city/delete/{cityId}")
    public ResponseEntity<GenericResponseDto<Null>> deleteCity(@Valid @PathVariable long cityId) {
        locationService.deleteLocation(LocationTree.CITY,cityId);
        GenericResponseDto<Null> response = new GenericResponseDto<>("City Deleted with id :" + cityId, true, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}
