package net.wedjaa.wetnet.business.dao;


import java.util.List;

import net.wedjaa.wetnet.business.domain.Alarms;
import net.wedjaa.wetnet.business.domain.Events;

import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.Users;


/**
 * @author graziella cipolletti
 *
 */
public interface AlarmsDAO {

    /**
     * 
     * @param user 
     * @return
     */
    public List<Alarms> getAll(Users user);

    /**
     * 
     * @param measure
     * @param user 
     * @return
     */
    public List<Alarms> getAllByMeasures(Measures measure,Users user);
    
    public Alarms getLastByMeasures(Measures measure,Users user);
    
    /**
     * 
     * @param user 
     * @return
     */
    public List<Alarms> getAllActive(Users user);

    /**
     * 
     * @param measure
     * @param user 
     * @return
     */
    public List<Alarms> getAllActiveByMeasures(Measures measure,Users user);
    
    /**
     * 
     * @param user 
     * @return
     */
    public List<Alarms> getAllClose(Users user);

    /**
     * 
     * @param measure
     * @param user 
     * @return
     */
    public List<Alarms> getAllCloseByMeasures(Measures measure,Users user);
    
  
}
