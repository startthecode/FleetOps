package org.samtar.warehouse.security.interceptor;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.samtar.warehouse.common.enums.Roles;
import org.samtar.warehouse.common.exceptions.AuthException;
import org.samtar.warehouse.common.exceptions.GenericException;
import org.samtar.warehouse.security.annotations.RequirePermissions;
import org.samtar.warehouse.shared.services.UserSharedService;
import org.samtar.warehouse.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RequirePermissionINTRC {
    @Autowired
    UserSharedService userSharedService;

    @Before("@annotation(requirePermissions)")
    public void checkPermission(RequirePermissions requirePermissions) {

        Roles currentUserRole = userSharedService.getCurrentUser().getRole();
        userSharedService.getCurrentUser();
        boolean allowed = Arrays.stream(requirePermissions.roles())
                .anyMatch(role -> role == currentUserRole);

        if (!allowed) {
            throw  AuthException.unAuthorized();
        }
    }

}
