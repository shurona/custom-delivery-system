package com.webest.store.common.aop;

import com.webest.store.store.exception.StoreErrorCode;
import com.webest.store.store.exception.StoreException;
import com.webest.web.common.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class RoleCheckAspect {

    private final HttpServletRequest request;

    @Before("@annotation(roleCheck)")
    public void checkRole(RoleCheck roleCheck) {
        String roleHeader = request.getHeader("X-Role");
        System.out.println(roleHeader);
        if (roleHeader == null) {
            throw new StoreException(StoreErrorCode.UNAUTHORIZED_ACCESS);
        }

        UserRole userRole;

        try {
            userRole = UserRole.valueOf(roleHeader);

        } catch (IllegalArgumentException e) {
            throw new StoreException(StoreErrorCode.UNAUTHORIZED_ACCESS);
        }

        UserRole requiredRole = roleCheck.requiredRole();

        if (userRole != requiredRole) {
            throw new StoreException(StoreErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}
