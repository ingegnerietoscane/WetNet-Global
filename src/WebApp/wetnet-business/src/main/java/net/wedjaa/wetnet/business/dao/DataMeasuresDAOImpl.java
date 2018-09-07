package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.dao.params.DataMeasuresFilter;
import net.wedjaa.wetnet.business.domain.DataMeasures;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public class DataMeasuresDAOImpl implements DataMeasuresDAO {

    private static final Logger logger = Logger.getLogger(DataMeasuresDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataMeasures> getAVGOnDays(Date startDate, Date endDate, long idMeasures) {
        try {
            String start = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
            String end = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
            DataMeasuresFilter filter = new DataMeasuresFilter(start, end, idMeasures);
            List<DataMeasures> result = sqlSessionTemplate.selectList("dataMeasures.getDataMeasuresAVGOnDaysFiltered", filter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataMeasures> getAVGOnHours(Date startDate, Date endDate, long idMeasures) {
        try {
            String start = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
            String end = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
            DataMeasuresFilter filter = new DataMeasuresFilter(start, end, idMeasures);
            List<DataMeasures> result = sqlSessionTemplate.selectList("dataMeasures.getDataMeasuresAVGOnHoursFiltered", filter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
       
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataMeasures> getAllFiltered(Date startDate, Date endDate, long idMeasures) {
        try {
            String start = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
            String end = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
            DataMeasuresFilter filter = new DataMeasuresFilter(start, end, idMeasures);
            List<DataMeasures> result = sqlSessionTemplate.selectList("dataMeasures.getAllDataMeasuresFiltered", filter);
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
	public List<DataMeasures> getAVG(Date startDate, Date endDate, long idMeasures) {
		 try {
	            String start = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
	            String end = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
	            DataMeasuresFilter filter = new DataMeasuresFilter(start, end, idMeasures);
	            List<DataMeasures> result = sqlSessionTemplate.selectList("dataMeasures.getDataMeasuresAVGFiltered", filter);
	            return result;
	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
    
	/*GC 18/11/2015*/
	/**
     * {@inheritDoc}
     */
	@Override
	public List<DataMeasures> getAVGOnMonths(Date startDate, Date endDate, long idMeasures) {
        try {
            String start = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
            String end = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
            DataMeasuresFilter filter = new DataMeasuresFilter(start, end, idMeasures);
            List<DataMeasures> result = sqlSessionTemplate.selectList("dataMeasures.getDataMeasuresAVGOnMonthsFiltered", filter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
	/**
     * {@inheritDoc}
     */
	@Override
    public List<DataMeasures> getAVGOnYears(Date startDate, Date endDate, long idMeasures){
        try {
            String start = DateUtil.SDTF2SIMPLEUSA.print(startDate.getTime());
            String end = DateUtil.SDTF2SIMPLEUSA.print(endDate.getTime());
            DataMeasuresFilter filter = new DataMeasuresFilter(start, end, idMeasures);
            List<DataMeasures> result = sqlSessionTemplate.selectList("dataMeasures.getDataMeasuresAVGOnYearsFiltered", filter);
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
}
