package org.samtar.warehouse.shared.services;

import org.samtar.warehouse.common.exceptions.AuthException;
import org.samtar.warehouse.user.entity.UserEntity;
import org.samtar.warehouse.user.service.imp.UserPrincipleImp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSharedService {

    public UserEntity getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof UserPrincipleImp principal)) {
            throw AuthException.sessionExpired();
        }
        return principal.getUser();
    }

}
