package net.wedjaa.wetnet.security;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import net.wedjaa.wetnet.business.dao.UsersDAO;
import net.wedjaa.wetnet.business.domain.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * @author alessandro vincelli
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = usersDAO.getByUserName(username);
        return new UserDetailsImpl(u.getUsername(), u.getPassword(), u.getIdusers(), u, Arrays.asList(Roles.ROLES.get(u.getRole())));
    }

    @PostConstruct
    public void initApp() {
        Users admin = usersDAO.getByUserName("admin");
        if (admin == null) {
            admin = new Users();
            admin.setEmail("admin@admin");
            admin.setName("admin");
            admin.setUsername("admin");
            admin.setPassword(messageDigestPasswordEncoder.encodePassword("admin", null));
            admin.setRole(3);
            usersDAO.saveOrUpdate(admin);
        }
    }
}
