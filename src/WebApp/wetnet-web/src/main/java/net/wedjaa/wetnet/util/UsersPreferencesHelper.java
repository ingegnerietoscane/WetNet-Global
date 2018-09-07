/**
 * 
 */
package net.wedjaa.wetnet.util;

import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersPreferences;
import net.wedjaa.wetnet.security.SecurityContextHelper;

/**
 * @author massimo ricci
 *
 */
public final class UsersPreferencesHelper {

    private UsersPreferencesHelper() {
    }
    
    public static UsersPreferences getUsersPreferences(){
        UsersPreferences usersPreferences = new UsersPreferences(SecurityContextHelper.getAuthenticatedUser());      
        return usersPreferences;
    }
    
    public static UsersPreferences setUsersPreferences(UsersPreferences usersPreferences){
        Users users = SecurityContextHelper.getAuthenticatedUser();
        users.setDistrictId(usersPreferences.getDistrictId());
        return usersPreferences;
    }
}
