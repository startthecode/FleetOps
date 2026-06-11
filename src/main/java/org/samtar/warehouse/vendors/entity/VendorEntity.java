package org.samtar.warehouse.vendors.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.samtar.warehouse.common.embedded.UserChild;
import org.samtar.warehouse.user.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vendors")
public class VendorEntity extends UserEntity {
    @Embedded
    UserChild userChild;
}
