/**
 * 
 */
package net.wedjaa.wetnet.business.dao;

import java.util.logging.Logger;

import net.wedjaa.wetnet.business.domain.DistrictsBandsHistory;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author massimo ricci
 *
 */
public class DistrictsBandsHistoryDAOImpl implements DistrictsBandsHistoryDAO {

    private static final Logger logger = Logger.getLogger("DistrictsBandsHistoryDAOImpl");

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DistrictsBandsHistory insertDistrictsBandsHistory(DistrictsBandsHistory districtsBandsHistory) {
        sqlSessionTemplate.insert("districtsBandsHistory.insertDistrictsBandsHistory", districtsBandsHistory);
        return districtsBandsHistory;
    }
    
}
