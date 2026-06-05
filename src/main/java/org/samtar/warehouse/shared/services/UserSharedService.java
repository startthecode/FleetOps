package org.samtar.warehouse.shared.services;

import org.samtar.warehouse.user.entity.UserEntity;
import org.samtar.warehouse.user.service.imp.UserPrincipleImp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserSharedService {

    public UserEntity getCurrentUser(){
        Authentication auth = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication(),"Something went wrong");
        UserPrincipleImp principal = Objects.requireNonNull(((UserPrincipleImp) auth.getPrincipal()),"Something went wrong");
        return  principal.getUser();
    }

}
