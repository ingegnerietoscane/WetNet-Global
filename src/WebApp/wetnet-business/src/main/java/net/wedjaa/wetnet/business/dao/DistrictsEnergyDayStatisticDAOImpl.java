/**
 * 
 */
package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.dao.params.DataDistrictsFilter;
import net.wedjaa.wetnet.business.domain.EpdIedIela;
import net.wedjaa.wetnet.business.domain.Users;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author massimo ricci
 *
 */
public class DistrictsEnergyDayStatisticDAOImpl implements DistrictsEnergyDayStatisticDAO {

    private static final Logger logger = Logger.getLogger(DistrictsEnergyDayStatisticDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    @Override
    public List<EpdIedIela> getDiffByDateAndDistrictId(long ditrictId, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getDiffByDateAndDistrictId'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getDiffByDateAndDistrictId", new DataDistrictsFilter(startDate, endDate, ditrictId));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EpdIedIela> getDiffByDateAndZone(String zone, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getDiffByDateAndZone'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getDiffByDateAndZone", new DataDistrictsFilter(startDate, endDate, zone, ""));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EpdIedIela> getDiffByDateAndMunicipality(String municipality, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getDiffByDateAndMunicipality'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getDiffByDateAndMunicipality", new DataDistrictsFilter(startDate, endDate, "", municipality));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EpdIedIela> getAllDistrictsByDate(Users user, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getAllDistrictsByDate'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getAllDistrictsByDate", new DataDistrictsFilter(user, startDate, endDate));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EpdIedIela> getAllMunicipalitiesByDate(Users user, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getAllMunicipalitiesByDate'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getAllMunicipalitiesByDate", new DataDistrictsFilter(user, startDate, endDate));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EpdIedIela> getAllZonesByDate(Users user, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getAllZonesByDate'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getAllZonesByDate", new DataDistrictsFilter(user, startDate, endDate));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }

    @Override
    public List<EpdIedIela> getDistrictEnergyByDate(long ditrictId, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getDistrictEnergyByDate'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getDistrictEnergyByDate", new DataDistrictsFilter(startDate, endDate, ditrictId));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    @Override
    public List<EpdIedIela> getZoneEnergyByDate(String zone, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getZoneEnergyByDate'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getZoneEnergyByDate", new DataDistrictsFilter(startDate, endDate, zone, ""));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    @Override
    public List<EpdIedIela> getMunicipalityEnergyByDate(String municipality, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getMunicipalityEnergyByDate'");
            List<EpdIedIela> result = sqlSessionTemplate.selectList("districtsEnergyDayStatistic.getMunicipalityEnergyByDate", new DataDistrictsFilter(startDate, endDate, "", municipality));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
}
