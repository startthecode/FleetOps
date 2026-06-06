package org.samtar.warehouse.location.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "countries")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_id_generator")
    @SequenceGenerator(name = "country_id_generator", sequenceName = "country_id_generator", allocationSize = 10)
    Long countryId;

    @NotBlank(message = "Country name can not be blank")
    @Size(max = 180, min = 2, message = "Country name must be between 2 to 30 character")
    @Column(name = "country_name", nullable = false, unique = true, length = 30)
    String countryName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "country")
    List<StateEntity> states = new ArrayList<>();
}
