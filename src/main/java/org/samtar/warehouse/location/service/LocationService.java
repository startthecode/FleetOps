package org.samtar.warehouse.location.service;

import org.samtar.warehouse.common.enums.LocationTree;
import org.samtar.warehouse.common.exceptions.LocationException;
import org.samtar.warehouse.location.dto.req.CreateCityReqDto;
import org.samtar.warehouse.location.dto.req.CreateCountryReqDto;
import org.samtar.warehouse.location.dto.req.CreateStateReqDto;
import org.samtar.warehouse.location.dto.req.UpdateReqDto;
import org.samtar.warehouse.location.dto.res.CityResponseDto;
import org.samtar.warehouse.location.dto.res.LocationResDto;
import org.samtar.warehouse.location.dto.res.StateResponseDto;
import org.samtar.warehouse.location.entity.CityEntity;
import org.samtar.warehouse.location.entity.CountryEntity;
import org.samtar.warehouse.location.entity.StateEntity;
import org.samtar.warehouse.location.mapper.CityMapper;
import org.samtar.warehouse.location.mapper.LocationMapper;
import org.samtar.warehouse.location.mapper.StateMapper;
import org.samtar.warehouse.location.repository.CityRepository;
import org.samtar.warehouse.location.repository.CountryRepository;
import org.samtar.warehouse.location.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    // Mappers
    LocationMapper locationMapper;
    CityMapper cityMapper;
    StateMapper stateMapper;

    // Repositories
    CountryRepository countryRepository;
    StateRepository stateRepository;
    CityRepository cityRepository;

    public LocationService(CityRepository cityRepository,
                           CityMapper cityMapper,
                           CountryRepository countryRepository,
                           LocationMapper locationMapper,
                           StateMapper stateMapper,
                           StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.countryRepository = countryRepository;
        this.locationMapper = locationMapper;
        this.stateMapper = stateMapper;
        this.stateRepository = stateRepository;
    }

    public LocationResDto createCountry(CreateCountryReqDto payload){
        locationExistOrThrow(LocationTree.COUNTRY, payload.countryName());
        CountryEntity newCountry = new CountryEntity();
        newCountry.setCountryName(payload.countryName());
        return locationMapper.singleItemToResponse(countryRepository.save(newCountry));
    }

    public LocationResDto createState(CreateStateReqDto payload){
        locationExistOrThrow(LocationTree.STATE, payload.stateName());
        locationNotExistOrThrow(LocationTree.COUNTRY, payload.countryID());
        StateEntity newState = new StateEntity();
        newState.setStateName(payload.stateName());
        newState.setCountry(countryRepository.getReferenceById(payload.countryID()));
        return locationMapper.singleItemToResponse(stateRepository.save(newState));
    }

    public LocationResDto createCity(CreateCityReqDto payload){
        locationExistOrThrow(LocationTree.CITY, payload.cityName());
        locationNotExistOrThrow(LocationTree.STATE, payload.stateID());
        CityEntity newCity = new CityEntity();
        newCity.setCityName(payload.cityName());
        newCity.setState(stateRepository.getReferenceById(payload.stateID()));
        return locationMapper.singleItemToResponse(cityRepository.save(newCity));
    }

    public List<LocationResDto> getAllCountries(){
        return countryRepository.findAll().stream().map(e->locationMapper.singleItemToResponse(e)).toList();
    }

    public StateResponseDto getStateByCountry(long countryID){
        CountryEntity country = countryRepository.findByCountryId(countryID).orElseThrow(()->LocationException.notExists(LocationTree.COUNTRY,countryID));
        return  stateMapper.toResponse(country);
    }

    public CityResponseDto getCityByState(long stateID){
        StateEntity city = stateRepository.findByStateId(stateID).orElseThrow(()->LocationException.notExists(LocationTree.STATE,stateID));
        return  cityMapper.toResponse(city);
    }

    public LocationResDto updateCountry(UpdateReqDto payload){
        locationExistOrThrow(LocationTree.COUNTRY,payload.newName());
        CountryEntity country = countryRepository.findByCountryId(payload.id()).orElseThrow(()->LocationException.notExists(LocationTree.COUNTRY,payload.id()));
        country.setCountryName(payload.newName());
        return locationMapper.singleItemToResponse(countryRepository.save(country));

    }

    public LocationResDto updateState(UpdateReqDto payload){
        locationExistOrThrow(LocationTree.STATE,payload.newName());
        StateEntity state = stateRepository.findByStateId(payload.id()).orElseThrow(()->LocationException.notExists(LocationTree.STATE,payload.id()));
        state.setStateName(payload.newName());
        return locationMapper.singleItemToResponse(stateRepository.save(state));
    }

    public LocationResDto updateCity(UpdateReqDto payload){
        locationExistOrThrow(LocationTree.CITY,payload.newName());
        CityEntity state = cityRepository.findByCityId(payload.id()).orElseThrow(()->LocationException.notExists(LocationTree.CITY,payload.id()));
        state.setCityName(payload.newName());
        return locationMapper.singleItemToResponse(cityRepository.save(state));
    }

    public void deleteLocation(LocationTree locationTree,Long locationID){
        locationNotExistOrThrow(locationTree,locationID);
        if(locationTree == LocationTree.COUNTRY) countryRepository.deleteById(locationID);
        if(locationTree == LocationTree.STATE) stateRepository.deleteById(locationID);
        if(locationTree == LocationTree.CITY) cityRepository.deleteById(locationID);
    }

    private void locationExistOrThrow(LocationTree locationTree,String locationName){
        if(locationTree == LocationTree.COUNTRY && countryRepository.existsByCountryNameIgnoreCase(locationName)){
            throw LocationException.alreadyExists(locationTree,locationName);
        }
        if(locationTree == LocationTree.STATE && stateRepository.existsByStateNameIgnoreCase(locationName)){
            throw LocationException.alreadyExists(locationTree,locationName);
        }
        if(locationTree == LocationTree.CITY && cityRepository.existsByCityNameIgnoreCase(locationName)){
            throw LocationException.alreadyExists(locationTree,locationName);
        }
    }
    private void locationNotExistOrThrow(LocationTree locationTree,long locationID){
        if(locationTree == LocationTree.COUNTRY && !countryRepository.existsByCountryId(locationID)){
            throw LocationException.notExists(locationTree,locationID);
        }
        if(locationTree == LocationTree.STATE && !stateRepository.existsByStateId(locationID)){
            throw LocationException.notExists(locationTree,locationID);
        }
        if(locationTree == LocationTree.CITY && !cityRepository.existsByCityId(locationID)){
            throw LocationException.notExists(locationTree,locationID);
        }
    }

}
