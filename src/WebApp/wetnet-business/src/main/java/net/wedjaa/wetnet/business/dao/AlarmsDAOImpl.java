package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.dao.params.DataAlarmsFilter;
import net.wedjaa.wetnet.business.dao.params.DataDistrictsFilter;
import net.wedjaa.wetnet.business.domain.Alarms;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsTypeNum;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.Users;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author graziella cipolletti
 *
 */
public class AlarmsDAOImpl implements AlarmsDAO {

    private static final Logger logger = Logger.getLogger(AlarmsDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Alarms> getAll(Users user) {
        try {
            logger.info("Received request for 'Alarms getAll'");
            List<Alarms> result = sqlSessionTemplate.selectList("alarms.getAll",new DataAlarmsFilter(user,null));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    

	@Override
	public List<Alarms> getAllByMeasures(Measures measure,Users user) {
		 try {
	            logger.info("Received request for 'Alarms getAllByMeasures'");
	            List<Alarms> result = sqlSessionTemplate.selectList("alarms.getAllByMeasure",new DataAlarmsFilter(measure,user,null));
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
	
	@Override
	public Alarms getLastByMeasures(Measures measure,Users user) {
		 try {
	            logger.info("Received request for 'Alarms getLastByMeasure'");
	            Alarms result = sqlSessionTemplate.selectOne("alarms.getLastByMeasure",new DataAlarmsFilter(measure,user,null));
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}


	@Override
	public List<Alarms> getAllActive(Users user) {
		 try {
	            logger.info("Received request for 'Alarms getAllActive'");
	            List<Alarms> result = sqlSessionTemplate.selectList("alarms.getAllActive",new DataAlarmsFilter(user,null));
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}


	@Override
	public List<Alarms> getAllActiveByMeasures(Measures measure, Users user) {
		 try {
	            logger.info("Received request for 'Alarms getAllActiveByMeasures'");
	            List<Alarms> result = sqlSessionTemplate.selectList("alarms.getAllActiveByMeasure",new DataAlarmsFilter(measure,user,null));
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}


	@Override
	public List<Alarms> getAllClose(Users user) {
		try {
            logger.info("Received request for 'Alarms getAllClose'");
            List<Alarms> result = sqlSessionTemplate.selectList("alarms.getAllClose",new DataAlarmsFilter(user,null));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
	}


	@Override
	public List<Alarms> getAllCloseByMeasures(Measures measure, Users user) {
		 try {
	            logger.info("Received request for 'Alarms getAllCloseByMeasures'");
	            List<Alarms> result = sqlSessionTemplate.selectList("alarms.getAllCloseByMeasure",new DataAlarmsFilter(measure,user,null));
	            return result;

	        } catch (Exception e) {
	            throw new BusinessesException(e);
	        }
	}
}
