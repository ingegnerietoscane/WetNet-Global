package net.wedjaa.wetnet.business.dao;

import java.util.List;
import java.util.logging.Logger;
import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.domain.UsersCFGSChild;
import net.wedjaa.wetnet.business.domain.UsersCFGSParent;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author roberto cascelli
 *
 */
public class UsersCFGSChildDAOImpl implements UsersCFGSChildDAO {

    private static final Logger logger = Logger.getLogger(UsersCFGSChildDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveChild(UsersCFGSChild cfgsChild) {
        try {
        	
            int result = sqlSessionTemplate.insert("userscfgschild.insertChild", cfgsChild);
            if (result == 0) {
                logger.info("no row modified for " + ReflectionToStringBuilder.toString(cfgsChild));
                return false;
            }else{
            	return true;
            }
        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<UsersCFGSChild> getAllParameters(long user) {
        try {
            List<UsersCFGSChild> paramList = sqlSessionTemplate.selectList("userscfgschild.getById", user);
            return paramList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
}
