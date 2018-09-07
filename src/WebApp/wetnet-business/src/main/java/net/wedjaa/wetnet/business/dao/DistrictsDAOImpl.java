package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.dao.params.DataDistrictsFilter;
import net.wedjaa.wetnet.business.domain.DataDistricts;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsBandsHistory;
import net.wedjaa.wetnet.business.domain.MeasuresHasDistricts;
import net.wedjaa.wetnet.business.domain.Users;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
public class DistrictsDAOImpl implements DistrictsDAO {

    private static final Logger logger = Logger.getLogger("DistrictsDAOImpl");

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Districts> getAllDistricts() {
        try {
            List<Districts> districtsList = getAllDistricts(null);
            return districtsList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Districts> getAllDistricts(Users user) {
        try {
            List<Districts> districtsList = sqlSessionTemplate.selectList("districts.getAllDistricts", user);
            return districtsList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Districts saveOrUpdate(Districts districts) {
        if (districts.getIdDistricts() == 0) {
            sqlSessionTemplate.insert("districts.insertDistricts", districts);
        } else {
            sqlSessionTemplate.update("districts.updateDistricts", districts);
        }
        return districts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long id) {
        sqlSessionTemplate.delete("districts.deleteDistricts", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Districts getById(long id) {
        return sqlSessionTemplate.selectOne("districts.getById", id);
    }
  
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Districts> getAllZones(Users user) {
        try {
            List<Districts> districtsList = sqlSessionTemplate.selectList("districts.getAllZones", user);
            return districtsList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Districts> getAllMunicipalities(Users user) {
        try {
            List<Districts> districtsList = sqlSessionTemplate.selectList("districts.getAllMunicipalities", user);
            return districtsList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    //***RC 04/11/2015***
    @Override
    public List<Districts> getAllWaterAuthorities(Users user) {
        try {
            List<Districts> districtsList = sqlSessionTemplate.selectList("districts.getAllWaterAuthorities", user);
            return districtsList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    //***END***
    
    /**
     * {@inheritDoc}
     */
    @Override
    /* GC - 22/10/2015 */
    /*public void addMeasures(long id_districts, long id_measures, long sign, long connections_id_odbcdsn) {
        MeasuresHasDistricts measuresHasDistricts = new MeasuresHasDistricts(id_districts, id_measures, sign, connections_id_odbcdsn);
        sqlSessionTemplate.insert("districts.insertMeasuresHasDistricts", measuresHasDistricts);
    }*/
    public void addMeasures(long id_districts, long id_measures, long sign) {
        MeasuresHasDistricts measuresHasDistricts = new MeasuresHasDistricts(id_districts, id_measures, sign);
        sqlSessionTemplate.insert("districts.insertMeasuresHasDistricts", measuresHasDistricts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeasuresHasDistricts> getMeasuresHasDistrictsByDistrictsId(long id_districts) {
        return sqlSessionTemplate.selectList("districts.measuresHasDistrictsBydIdDistricts", id_districts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeMeasuresHasDistricts(long measures_id, long districts_id) {
        MeasuresHasDistricts measuresHasDistricts = new MeasuresHasDistricts(districts_id, measures_id);
        sqlSessionTemplate.delete("districts.deleteMeasuresHasDistricts", measuresHasDistricts);
    }

    /* GC 12/11/2015*/
	@Override
	public List<DistrictsBandsHistory> getAllDistrictsBandsHistory(long id) {
		try {
            List<DistrictsBandsHistory> districtsBHList = sqlSessionTemplate.selectList("districtsBandsHistory.getAllBandsHistory", id);
            
            return districtsBHList;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
	}

	/* GC 13/11/2015*/
	@Override
	public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictonDays(Date startDate, Date endDate, long idDistricts) {
		 try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsBandsHistory> result = sqlSessionTemplate.selectList("districtsBandsHistory.getBandsHistoryByDateDistrictsonDays", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
	
	
	/*GC 16/12/2016 restituisce gli ultimi valori di bands history utili*/
	public List<DistrictsBandsHistory> getFirstBandsHistoryByDistrictsOnTimestampAsc(Date startDate, Date endDate, long idDistricts){
		 try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsBandsHistory> result = sqlSessionTemplate.selectList("districtsBandsHistory.getFirstBandsHistoryByDistrictsOnTimestampAsc", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
	
	
	public List<DistrictsBandsHistory> getLastBandsHistoryByDistrictsOnTimestampDesc(Date startDate, Date endDate,
			long idDistricts)
	{
		try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsBandsHistory> result = sqlSessionTemplate.selectList("districtsBandsHistory.getLastBandsHistoryByDistrictsOnTimestampDesc", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
	
	public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictAll(Date startDate, Date endDate, long idDistricts){
		 try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsBandsHistory> result = sqlSessionTemplate.selectList("districtsBandsHistory.getBandsHistoryByDateDistrictsAll", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
	public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictonHours(Date startDate, Date endDate, long idDistricts){
		 try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsBandsHistory> result = sqlSessionTemplate.selectList("districtsBandsHistory.getBandsHistoryByDateDistrictsonHours", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
	public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictonMonths(Date startDate, Date endDate, long idDistricts){
		 try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsBandsHistory> result = sqlSessionTemplate.selectList("districtsBandsHistory.getBandsHistoryByDateDistrictsonMonths", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
	public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictonYears(Date startDate, Date endDate, long idDistricts){
		 try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsBandsHistory> result = sqlSessionTemplate.selectList("districtsBandsHistory.getBandsHistoryByDateDistrictsonYears", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
	/* GC 13/11/2015*/
	@Override
	public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictAVG(Date startDate, Date endDate, long idDistricts) {
		 try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DistrictsBandsHistory> result = sqlSessionTemplate.selectList("districtsBandsHistory.getBandsHistoryAVGByDateDistricts", objFilter);
	            
	            
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
}
