package org.samtar.warehouse.location.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "states")
public class StateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_id_generator")
    @SequenceGenerator(name = "state_id_generator", sequenceName = "state_id_generator", allocationSize = 10)
    Long stateId;

    @NotBlank(message = "State name can not be blank")
    @Size(max = 180, min = 2, message = "State name must be between 2 to 30 character")
    @Column(name = "state_name", nullable = false, unique = true, length = 30)
    String stateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    CountryEntity country;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "state")
    List<CityEntity> cities = new ArrayList<>();

}
