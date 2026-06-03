package org.samtar.warehouse.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.samtar.warehouse.common.enums.Roles;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator",sequenceName = "user_id_generator",allocationSize = 10)
    Long id;

    @NotBlank
    @Size(min = 5,max = 30)
    @Column(name = "name",unique = true,nullable = false,length = 30)
    String username;

    @NotBlank
    @Size(min = 6,max = 50)
    @Column(name = "email",unique = true,nullable = false,length = 50)
    String email;

    @NotNull
    @Size(min = 9,max = 12)
    @Column(name = "phone",unique = true,nullable = false,length = 12)
    Integer phone;

    @Column
    boolean isAccountBlocked = false;

    @Column
    @Enumerated(value = EnumType.STRING)
    Roles role;

}
