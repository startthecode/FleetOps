package org.samtar.warehouse.vendors.repository;

import org.samtar.warehouse.vendors.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
}
