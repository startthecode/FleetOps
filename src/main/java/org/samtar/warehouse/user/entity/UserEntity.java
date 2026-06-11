package org.samtar.warehouse.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.samtar.warehouse.common.enums.Roles;
import org.samtar.warehouse.location.entity.CityEntity;
import org.samtar.warehouse.location.entity.StateEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator",sequenceName = "user_id_generator",allocationSize = 10)
    Long id;

    @NotBlank
    @Size(min = 5,max = 30)
    @Column(unique = true,nullable = false,length = 30)
    String username;

    @NotBlank
    @Size(min = 6,max = 50)
    @Column(unique = true,nullable = false,length = 50)
    String email;
    
    @NotBlank
    @Size(min = 8,max = 80)
    @Column(nullable = false,length = 80)
    String password;

    @NotBlank
    @Size(min = 9,max = 12)
    @Column(unique = true,nullable = false,length = 12)
    String phone;

    @Column
    boolean isAccountBlocked = false;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Roles role;

    @ManyToOne
    @JoinColumn(nullable = true,name = "city")
    CityEntity city;


}
