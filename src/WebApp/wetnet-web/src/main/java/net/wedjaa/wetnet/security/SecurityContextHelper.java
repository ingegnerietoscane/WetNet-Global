/**
 * 
 */
package net.wedjaa.wetnet.security;

import net.wedjaa.wetnet.business.domain.Users;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public final class SecurityContextHelper {

    private SecurityContextHelper() {
    }

    /**
     * 
     * @return the authenticated user
     */
    public static Users getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&  authentication.getPrincipal() instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) authentication.getPrincipal()).getUsers();
        }
        return null;
    }

    /**
     * 
     * @return the authenticated userDetails
     */
    public static UserDetailsImpl getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (UserDetailsImpl) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 
     * @return true if the current user is authenticated
     */
    public static boolean isAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return true;
        }
        return false;
    }
    
}
