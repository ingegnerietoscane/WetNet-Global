package net.wedjaa.wetnet.business.dao;

import java.util.List;

import net.wedjaa.wetnet.business.domain.UsersCFGSChild;
import net.wedjaa.wetnet.business.domain.UsersCFGSParent;

/**
 * @author roberto cascelli
 *
 */
public interface UsersCFGSChildDAO {

    public boolean saveChild(UsersCFGSChild cfgsChild);
    
    public List<UsersCFGSChild> getAllParameters(long user);
}
