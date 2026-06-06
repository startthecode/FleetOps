package org.samtar.warehouse.location.repository;

import org.samtar.warehouse.location.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
    Optional<CityEntity> findByCityNameIgnoreCase(String cityName);
    Optional<CityEntity> findByCityId(long cityId);
    List<CityEntity> findByState_StateId(long stateId);
    Boolean existsByCityNameIgnoreCase(String cityName);
    Boolean existsByCityId(long cityId);
}
