package org.samtar.warehouse.location.repository;

import org.samtar.warehouse.location.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    Optional<CountryEntity> findByCountryNameIgnoreCase(String countryName);
    Optional<CountryEntity> findByCountryId(long countryId);
    Boolean existsByCountryNameIgnoreCase(String countryName);
    Boolean existsByCountryId(long countryId);
}
