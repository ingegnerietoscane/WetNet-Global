package net.wedjaa.wetnet.business.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersCFGSParent;
import net.wedjaa.wetnet.business.domain.UsersHasDistricts;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author roberto cascelli
 *
 */
public class UsersCFGSParentDAOImpl implements UsersCFGSParentDAO {

    private static final Logger logger = Logger.getLogger(UsersCFGSParentDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveConfiguration(UsersCFGSParent cfgsParent) {
        try {
        	
        	//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        	//String formattedDate = formatter.format(cfgsParent.getSave_date());
        	
        	//System.out.println(formattedDate);
        	
            int result = sqlSessionTemplate.insert("userscfgsparent.insertConfiguration", cfgsParent);
            if (result == 0) {
                logger.info("no row modified for " + ReflectionToStringBuilder.toString(cfgsParent));
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
    public List<UsersCFGSParent> getAllConfigurations(UsersCFGSParent parent) {
        try {
            List<UsersCFGSParent> configList = sqlSessionTemplate.selectList("userscfgsparent.getById", parent);
            return configList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeConfiguration(String parentDate) {
        try {
        	
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            Date startDate;
            startDate = df.parse(parentDate);
        	
            int result = sqlSessionTemplate.delete("userscfgsparent.removeConfiguration", startDate);
            if (result == 0) {
                logger.info("no row modified for " + ReflectionToStringBuilder.toString(parentDate));
                return false;
            }else{
            	return true;
            }
        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
}
