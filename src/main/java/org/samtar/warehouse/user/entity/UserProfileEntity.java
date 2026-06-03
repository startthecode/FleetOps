package org.samtar.warehouse.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity
@Table(name = "users_profile")
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_profile_id_generator")
    @SequenceGenerator(name = "user_profile_id_generator",sequenceName = "user_profile_id_generator",allocationSize = 10)
    Long id;

    @NotBlank
    @Size(min = 3,max = 20)
    @Column(name = "name",nullable = false,length = 20)
    String name;

    @NotBlank
    @Size(min = 3,max = 20)
    @Column(name = "last_name",nullable = false,length = 20)
    String lastName;

    @NotBlank
    @Size(min = 10,max = 500)
    @Column(name = "profile_picture",nullable = false,length = 500)
    String profilePicture;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    UserEntity user;

}
