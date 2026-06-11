package org.samtar.warehouse.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_profile")
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_profile_id_generator")
    @SequenceGenerator(name = "user_profile_id_generator",sequenceName = "user_profile_id_generator",allocationSize = 10)
    Long id;

    @NotBlank
    @Size(min = 3,max = 20)
    @Column(nullable = false,length = 20)
    String name;

    @NotBlank
    @Size(min = 3,max = 20)
    @Column(nullable = false,length = 20)
    String lastName;

    @NotBlank
    @Size(min = 10,max = 500)
    @Column(nullable = false,length = 500)
    String profilePicture;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    UserEntity user;

}
