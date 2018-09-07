/**
 * 
 */
package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.dao.params.DataDistrictsFilter;
import net.wedjaa.wetnet.business.domain.DayStatisticJoinDistrictsJoinEnergy;
import net.wedjaa.wetnet.business.domain.DistrictsDayStatistic;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author massimo ricci
 *
 */
public class DistrictsDayStatisticDAOImpl implements DistrictsDayStatisticDAO {

    private static final Logger logger = Logger.getLogger("DistrictsDayStatisticDAOImpl");

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<DistrictsDayStatistic> getAllDistrictsDayStatistic() {
        try {
            logger.info("Received request for 'getAllDistrictsDayStatistic'");

            List<DistrictsDayStatistic> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getAllDistrictsDayStatistic");
            return districtsDayStatisticList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }

    @Override
    public List<DistrictsDayStatistic> getDistrictsDayStatisticById(Date startDate, Date endDate, long idDistricts) {
        try {
            logger.info("Received request for 'getDistrictsDayStatisticById'");
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
            List<DistrictsDayStatistic> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDistrictsDayStatisticById", objFilter);
            return districtsDayStatisticList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergy(Date startDate, Date endDate, long idDistricts) {
        try {
            logger.info("Received request for 'getDayStatisticJoinDistrictsJoinEnergy'");
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
            List<DayStatisticJoinDistrictsJoinEnergy> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDayStatisticJoinDistrictsJoinEnergy", objFilter);
            return districtsDayStatisticList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<DistrictsDayStatistic> getDistrictsDayStatisticByDistrict(Date startDate, Date endDate, long idDistricts) {
        try {
            logger.info("Received request for 'getDistrictsDayStatisticByDistrict'");
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
            List<DistrictsDayStatistic> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDistrictsDayStatisticByDistrict", objFilter);
            return districtsDayStatisticList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<DistrictsDayStatistic> getDistrictsDayStatisticByMunicipality(Date startDate, Date endDate, String municipality) {
        try {
            logger.info("Received request for 'getDistrictsDayStatisticByMunicipality'");
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, "", municipality);
            List<DistrictsDayStatistic> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDistrictsDayStatisticByMunicipality", objFilter);
            return districtsDayStatisticList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<DistrictsDayStatistic> getDistrictsDayStatisticByZone(Date startDate, Date endDate, String zone) {
        try {
            logger.info("Received request for 'getDistrictsDayStatisticByZone'");
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, zone, "");
            List<DistrictsDayStatistic> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDistrictsDayStatisticByZone", objFilter);
            return districtsDayStatisticList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }

    
    /* GC 03/11/2015 */
	@Override
	public List<DistrictsDayStatistic> getDistrictsDayStatisticAvgById(Date startDate, Date endDate, long idDistricts) {
		 try {
	            logger.info("Received request for 'getDistrictsDayStatisticAvgById'");
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsDayStatistic> districtsDayStatisticAvgList = sqlSessionTemplate.selectList("districtsDayStatistic.getDistrictsDayStatisticAvgById", objFilter);
	            return districtsDayStatisticAvgList;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}

	 /* GC 04/11/2015 */
	@Override
	public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergyAvg(Date startDate,
			Date endDate, long idDistricts) {
		try {
            logger.info("Received request for 'getDayStatisticJoinDistrictsJoinEnergyAvg'");
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
            List<DayStatisticJoinDistrictsJoinEnergy> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDayStatisticJoinDistrictsJoinEnergyAvg", objFilter);
            return districtsDayStatisticList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
	}
	
	/*GC 16/11/2015*/
	 @Override
	    public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergyAVG(Date startDate, Date endDate, long idDistricts) {
	        try {
	            logger.info("Received request for 'getDayStatisticJoinDistrictsJoinEnergyAVG'");
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DayStatisticJoinDistrictsJoinEnergy> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDayStatisticJoinDistrictsJoinEnergyAvg", objFilter);
	            return districtsDayStatisticList;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }

	    }

	 /* GC 18/11/2015*/
	 @Override
	 public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergyonMonths(Date startDate, Date endDate, long idDistricts)
	 {
		 try {
	            logger.info("Received request for 'getDayStatisticJoinDistrictsJoinEnergyonMonths'");
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DayStatisticJoinDistrictsJoinEnergy> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDayStatisticJoinDistrictsJoinEnergyonMonths", objFilter);
	            return districtsDayStatisticList;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	 }
	
	 @Override
	 public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergyonYears(Date startDate, Date endDate, long idDistricts)
	 {
		 try {
	            logger.info("Received request for 'getDayStatisticJoinDistrictsJoinEnergyonYears'");
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DayStatisticJoinDistrictsJoinEnergy> districtsDayStatisticList = sqlSessionTemplate.selectList("districtsDayStatistic.getDayStatisticJoinDistrictsJoinEnergyonYears", objFilter);
	            return districtsDayStatisticList;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	 }
		
}
