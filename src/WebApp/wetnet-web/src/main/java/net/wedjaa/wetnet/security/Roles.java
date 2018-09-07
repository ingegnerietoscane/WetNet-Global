package net.wedjaa.wetnet.security;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class Roles {

    /**
     * 0
     */
    public static final SimpleGrantedAuthority USER = new SimpleGrantedAuthority("ROLE_USER");
    /**
     * 1
     */
    public static final SimpleGrantedAuthority OPERATOR = new SimpleGrantedAuthority("ROLE_OPERATOR");
    /**
     * 2
     */
    public static final SimpleGrantedAuthority SUPERVISOR = new SimpleGrantedAuthority("ROLE_SUPERVISOR");
    /**
     * 3
     */
    public static final SimpleGrantedAuthority ADMINISTRATOR = new SimpleGrantedAuthority("ROLE_ADMINISTRATOR");
    /**
     * 4
     */
    public static final SimpleGrantedAuthority METER_READER = new SimpleGrantedAuthority("ROLE_METER_READER");

    static final Map<Integer, SimpleGrantedAuthority> ROLES;
    static {
        Map<Integer, SimpleGrantedAuthority> map = new LinkedHashMap<Integer, SimpleGrantedAuthority>();
        map.put(0, USER);
        map.put(1, OPERATOR);
        map.put(2, SUPERVISOR);
        map.put(3, ADMINISTRATOR);
        map.put(4, METER_READER);
        ROLES = Collections.unmodifiableMap(map);
    }

    public final static boolean isROLE_USER(Collection<GrantedAuthority> collection) {
        for (GrantedAuthority grantedAuthority : collection) {
            if (grantedAuthority.getAuthority().equalsIgnoreCase(USER.getAuthority())) {
                return true;
            }
        }
        return false;
    }
    
    public final static boolean isROLE_OPERATOR(Collection<GrantedAuthority> collection) {
        for (GrantedAuthority grantedAuthority : collection) {
            if (grantedAuthority.getAuthority().equalsIgnoreCase(OPERATOR.getAuthority())) {
                return true;
            }
        }
        return false;
    }
    
    public final static boolean isROLE_METER_READER(Collection<GrantedAuthority> collection) {
        for (GrantedAuthority grantedAuthority : collection) {
            if (grantedAuthority.getAuthority().equalsIgnoreCase(METER_READER.getAuthority())) {
                return true;
            }
        }
        return false;
    }

}
