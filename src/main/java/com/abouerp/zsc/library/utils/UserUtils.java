package com.abouerp.zsc.library.utils;


import com.abouerp.zsc.library.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author Abouerp
 */
public class UserUtils {
    private UserUtils() {

    }

//    public static Optional<Integer> getCurrentAuditor() {
//        return Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(UserPrincipal.class::cast)
//                .map(UserPrincipal::getId);
//    }

    public static Optional<String> getCurrentAuditorUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(UserPrincipal.class::cast)
                .map(UserPrincipal::getUsername);
    }
}
