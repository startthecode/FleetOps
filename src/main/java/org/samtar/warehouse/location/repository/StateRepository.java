package org.samtar.warehouse.location.repository;

import org.samtar.warehouse.location.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Long> {
    Optional<StateEntity> findByStateNameIgnoreCase(String stateName);
    Optional<StateEntity> findByStateId(long stateId);
    List<StateEntity> findByCountry_CountryId(long countryId);
    Boolean existsByStateNameIgnoreCase(String stateName);
    Boolean existsByStateId(long stateId);
}
