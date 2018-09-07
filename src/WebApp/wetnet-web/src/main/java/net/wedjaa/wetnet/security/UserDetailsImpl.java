package net.wedjaa.wetnet.security;

import java.util.Collection;

import net.wedjaa.wetnet.business.domain.Users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @author alessandro vincelli
 *
 */
public class UserDetailsImpl extends User {

    private static final long serialVersionUID = 1L;
    private long idusers;
    private Users users;

    /**
     * 
     * @param username
     * @param password
     * @param idusers
     * @param users (user object) 
     * @param authorities
     */
    public UserDetailsImpl(String username, String password, long idusers, Users users, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idusers = idusers;
        this.users = users;
    }

    public long getIdusers() {
        return idusers;
    }

    public Users getUsers() {
        return users;
    }

    
}
