package org.samtar.warehouse.user.service.imp;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.samtar.warehouse.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipleImp implements UserDetails {

    UserEntity userEntity;

    public UserPrincipleImp(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(userEntity.getRole().name()));
    }

    @Override
    public @Nullable String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return !    userEntity.isAccountBlocked();
    }

    public UserEntity getUser(){
        return userEntity;
    }

}
