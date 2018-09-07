package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;

import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsTypeNum;
import net.wedjaa.wetnet.business.domain.Users;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public interface EventsDAO {

    /**
     * 
     * @return
     */
    public List<Events> getAll();

    /**
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Events> getAllByDate(Date startDate, Date endDate, Users user);
    
    /**
     * 
     * @param ditrictId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Events> getAllByDateAndDistrict(long ditrictId, Date startDate, Date endDate);
    
    /**
     * 
     * @param ditrictId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Events> getByDateAndDistrictId(long ditrictId, Date startDate, Date endDate);

    /**
     * 
     * @param zone
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Events> getByDateAndZone(String zone, Date startDate, Date endDate);
    
    /**
     * 
     * @param municipality
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Events> getByDateAndMunicipality(String municipality, Date startDate, Date endDate);
    
    /**
     * 
     * @param ditrictId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getEventsTypeNumByDistrict(long ditrictId, Date startDate, Date endDate);
    
    /**
     * 
     * @param zone
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getEventsTypeNumByZone(String zone, Date startDate, Date endDate);
    
    /**
     * 
     * @param municipality
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getEventsTypeNumByMunicipality(String municipality, Date startDate, Date endDate);
    
    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getEventsGroupByTypeDistrict(Date startDate, Date endDate, Users user);
    
    /**
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getEventsGroupByTypeZone(Date startDate, Date endDate, Users user);
    
    /**
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getEventsGroupByTypeMunicipality(Date startDate, Date endDate, Users user);
    
    /**
     * 
     * @param ditrictId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getDistrictEventsGroupByTypeDistrict(long ditrictId, Date startDate, Date endDate);
    
    /**
     * 
     * @param zone
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getZoneEventsGroupByTypeZone(String zone, Date startDate, Date endDate);
    
    /**
     * 
     * @param municipality
     * @param startDate
     * @param endDate
     * @return
     */
    public List<EventsTypeNum> getMunicipalityEventsGroupByTypeMunicipality(String municipality, Date startDate, Date endDate);
}
