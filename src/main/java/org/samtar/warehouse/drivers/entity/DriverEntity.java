package org.samtar.warehouse.drivers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.samtar.warehouse.common.embedded.UserChild;
import org.samtar.warehouse.user.entity.UserEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drivers")
public class DriverEntity extends UserEntity {
    @Embedded
    UserChild userChild;

    @NotNull(message = "not null")
    @Max(10)
    @Min(1)
    @Column(nullable = false)
    Integer driveRating;

    @NotNull(message = "not null")
    @Column(nullable = false)
    Boolean haveDrivingLicence;

}
