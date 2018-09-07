package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;

import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersCFGSParent;

/**
 * @author roberto cascelli
 *
 */
public interface UsersCFGSParentDAO {

    public boolean saveConfiguration(UsersCFGSParent cfgsParent);
    
    public List<UsersCFGSParent> getAllConfigurations(UsersCFGSParent parent);
    
    public boolean removeConfiguration(String parentDate);
}
