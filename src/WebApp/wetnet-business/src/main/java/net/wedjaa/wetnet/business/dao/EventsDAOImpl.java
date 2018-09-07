package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.dao.params.DataDistrictsFilter;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsTypeNum;
import net.wedjaa.wetnet.business.domain.Users;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public class EventsDAOImpl implements EventsDAO {

    private static final Logger logger = Logger.getLogger(EventsDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Events> getAll() {
        try {
            logger.info("Received request for 'getAll'");
            List<Events> result = sqlSessionTemplate.selectList("events.getAll");
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<Events> getAllByDate(Date startDate, Date endDate, Users user) {
        try {
            logger.info("Received request for 'getAllByDate'");
            List<Events> result = sqlSessionTemplate.selectList("events.getAllByDate", new DataDistrictsFilter(user, startDate, endDate));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }

    @Override
    public List<Events> getAllByDateAndDistrict(long ditrictId, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getAllByDateAndDistrict'");
            List<Events> result = sqlSessionTemplate.selectList("events.getAllByDateAndDistrict", new DataDistrictsFilter(startDate, endDate, ditrictId));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<Events> getByDateAndDistrictId(long ditrictId, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getbyDateAndDistrictId'");
            List<Events> result = sqlSessionTemplate.selectList("events.getbyDateAndDistrictId", new DataDistrictsFilter(startDate, endDate, ditrictId));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<Events> getByDateAndZone(String zone, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getByDateAndZone'");
            List<Events> result = sqlSessionTemplate.selectList("events.getbyDateAndZone", new DataDistrictsFilter(startDate, endDate, zone, ""));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<Events> getByDateAndMunicipality(String municipality, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getByDateAndMunicipality'");
            List<Events> result = sqlSessionTemplate.selectList("events.getbyDateAndMunicipality", new DataDistrictsFilter(startDate, endDate, "", municipality));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getEventsTypeNumByDistrict(long ditrictId, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getEventsTypeNumByDistrict'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getEventsTypeNumByDistrict", new DataDistrictsFilter(startDate, endDate, ditrictId));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getEventsTypeNumByZone(String zone, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getEventsTypeNumByZone'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getEventsTypeNumByZone", new DataDistrictsFilter(startDate, endDate, zone, ""));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getEventsTypeNumByMunicipality(String municipality, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getEventsTypeNumByMunicipality'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getEventsTypeNumByMunicipality", new DataDistrictsFilter(startDate, endDate, "", municipality));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getEventsGroupByTypeDistrict(Date startDate, Date endDate, Users user) {
        try {
            logger.info("Received request for 'getEventsGroupByTypeDistrict'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getEventsGroupByTypeDistrict", new DataDistrictsFilter(user, startDate, endDate));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getEventsGroupByTypeZone(Date startDate, Date endDate, Users user) {
        try {
            logger.info("Received request for 'getEventsGroupByTypeZone'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getEventsGroupByTypeZone", new DataDistrictsFilter(user, startDate, endDate));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getEventsGroupByTypeMunicipality(Date startDate, Date endDate, Users user) {
        try {
            logger.info("Received request for 'getEventsGroupByTypeMunicipality'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getEventsGroupByTypeMunicipality", new DataDistrictsFilter(user, startDate, endDate));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getDistrictEventsGroupByTypeDistrict(long ditrictId, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getDistrictEventsGroupByTypeDistrict'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getDistrictEventsGroupByTypeDistrict", new DataDistrictsFilter(startDate, endDate, ditrictId));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getZoneEventsGroupByTypeZone(String zone, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getZoneEventsGroupByTypeZone'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getZoneEventsGroupByTypeZone", new DataDistrictsFilter(startDate, endDate, zone, ""));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }

    }
    
    @Override
    public List<EventsTypeNum> getMunicipalityEventsGroupByTypeMunicipality(String municipality, Date startDate, Date endDate) {
        try {
            logger.info("Received request for 'getMunicipalityEventsGroupByTypeMunicipality'");
            List<EventsTypeNum> result = sqlSessionTemplate.selectList("events.getMunicipalityEventsGroupByTypeMunicipality", new DataDistrictsFilter(startDate, endDate, "", municipality));
            return result;

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
}
