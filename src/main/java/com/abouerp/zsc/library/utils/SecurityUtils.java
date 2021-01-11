package com.abouerp.zsc.library.utils;

import com.abouerp.zsc.library.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Abouerp
 */
public abstract class SecurityUtils {

    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return extractPrincipal(securityContext.getAuthentication());
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal springSecurityUser = (UserPrincipal) authentication.getPrincipal();
            if (springSecurityUser != null) {
                return springSecurityUser.getUsername();
            } else {
                return null;
            }
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
