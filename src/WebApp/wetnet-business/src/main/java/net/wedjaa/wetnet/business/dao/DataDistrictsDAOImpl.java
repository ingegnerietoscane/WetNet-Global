package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.dao.params.DataDistrictsFilter;
import net.wedjaa.wetnet.business.domain.DataDistricts;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author massimo ricci
 *
 */
public class DataDistrictsDAOImpl implements DataDistrictsDAO {

    private static final Logger logger = Logger.getLogger("DistrictsDAOImpl");

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getAllDataDistricts() {
        try {
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getAllDataDistricts");
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getDataDistrictsByDistrictId(long districtId) {
        try {

            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getDataDistrictsByDistrictId", districtId);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedDataDistrictsByDistrictId(long districtId) {
        try {
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedDataDistrictsByDistrictId", districtId);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedDataDistricts() {
        try {
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedDataDistricts");
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedDataDistrictsAVGOnDays(Date startDate, Date endDate, long districtId) {
        try {
           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedDataDistrictsFilteredOnDatesAVGonDays", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedDataDistrictsAVGOnHours(Date startDate, Date endDate, long districtId) {
        try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedDataDistrictsFilteredOnDatesAVGonHours", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedAllDataDistricts(Date startDate, Date endDate, long districtId) {
        try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedAllDataDistrictsFilteredOnDates", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedEnergyProfileAVGOnDays(Date startDate, Date endDate, long districtId) {
        try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedEnergyProfileFilteredOnDatesAVGonDays", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedEnergyProfileAVGOnHours(Date startDate, Date endDate, long districtId) {
        try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedEnergyProfileFilteredOnDatesAVGonHours", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedAllEnergyProfile(Date startDate, Date endDate, long districtId) {
        try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedAllEnergyProfileFilteredOnDates", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedLossesProfileAVGOnDays(Date startDate, Date endDate, long districtId) {
        try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedLossesProfileFilteredOnDatesAVGonDays", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedLossesProfileAVGOnHours(Date startDate, Date endDate, long districtId) {
        try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedLossesProfileFilteredOnDatesAVGonHours", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataDistricts> getJoinedAllLossesProfile(Date startDate, Date endDate, long districtId) {
        try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedAllLossesProfileFilteredOnDates", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    
    
    /* GC 03/11/2015 */
    
    /**
     * {@inheritDoc}
     */
	@Override
	public List<DataDistricts> getJoinedDataDistrictsAVG(Date startDate, Date endDate, long districtId) {
		try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
	            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedDataDistrictsFilteredOnDatesAVG", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}

	 /* GC 03/11/2015 */
	/**
     * {@inheritDoc}
     */
	@Override
	public List<DataDistricts> getJoinedEnergyProfileAVG(Date startDate, Date endDate, long districtId) {
		try {
            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedEnergyProfileFilteredOnDatesAVG", objFilter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
	}

	 /* GC 03/11/2015 */
	/**
     * {@inheritDoc}
     */
	@Override
	public List<DataDistricts> getJoinedLossesProfileAVG(Date startDate, Date endDate, long districtId) {
		 try {
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
	            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedLossesProfileFilteredOnDatesAVG", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}

	
	/* GC 18/11/2015*/
	@Override
	public List<DataDistricts> getJoinedDataDistrictsAVGOnMonths(Date startDate, Date endDate, long idDistricts) {
		try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedDataDistrictsFilteredOnDatesAVGonMonths", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}

	@Override
	public List<DataDistricts> getJoinedDataDistrictsAVGOnYears(Date startDate, Date endDate, long idDistricts) {
		try {
	           DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedDataDistrictsFilteredOnDatesAVGonYears", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}

	@Override
	public List<DataDistricts> getJoinedEnergyProfileAVGOnMonths(Date startDate, Date endDate, long idDistricts) {
		 try {
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedEnergyProfileFilteredOnDatesAVGonMonths", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}

	@Override
	public List<DataDistricts> getJoinedEnergyProfileAVGOnYears(Date startDate, Date endDate, long idDistricts) {
		 try {
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, idDistricts);
	            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedEnergyProfileFilteredOnDatesAVGonYears", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}

	 @Override
	    public List<DataDistricts> getJoinedLossesProfileAVGOnMonths(Date startDate, Date endDate, long districtId) {
	        try {
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
	            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedLossesProfileFilteredOnDatesAVGonMonths", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	    }
	 
	 @Override
	    public List<DataDistricts> getJoinedLossesProfileAVGOnYears(Date startDate, Date endDate, long districtId) {
	        try {
	            DataDistrictsFilter objFilter = new DataDistrictsFilter(startDate, endDate, districtId);
	            List<DataDistricts> result = sqlSessionTemplate.selectList("dataDistricts.getJoinedLossesProfileFilteredOnDatesAVGonYears", objFilter);
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	    }
}
