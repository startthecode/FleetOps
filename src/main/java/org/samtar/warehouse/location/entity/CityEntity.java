package org.samtar.warehouse.location.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.samtar.warehouse.user.entity.UserEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cities")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_id_generator")
    @SequenceGenerator(name = "city_id_generator", sequenceName = "city_id_generator", allocationSize = 10)
    Long cityId;

    @NotBlank(message = "City name can not be blank")
    @Size(max = 180, min = 2, message = "City name must be between 2 to 30 character")
    @Column(name = "city_name", nullable = false, unique = true, length = 30)
    String cityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state")
    StateEntity state;

    @OneToMany(mappedBy = "city")
    Set<UserEntity> users = new HashSet<>();
}
