/**
 * 
 */
package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;

import net.wedjaa.wetnet.business.domain.EpdIedIela;
import net.wedjaa.wetnet.business.domain.Users;

/**
 * @author massimo ricci
 *
 */
public interface DistrictsEnergyDayStatisticDAO {

    public List<EpdIedIela> getDiffByDateAndDistrictId(long ditrictId, Date startDate, Date endDate);
    
    public List<EpdIedIela> getDiffByDateAndZone(String zone, Date startDate, Date endDate);
    
    public List<EpdIedIela> getDiffByDateAndMunicipality(String municipality, Date startDate, Date endDate);
    
    public List<EpdIedIela> getAllDistrictsByDate(Users user, Date startDate, Date endDate);
    
    public List<EpdIedIela> getAllMunicipalitiesByDate(Users user, Date startDate, Date endDate);
    
    public List<EpdIedIela> getAllZonesByDate(Users user, Date startDate, Date endDate);
    
    public List<EpdIedIela> getDistrictEnergyByDate(long ditrictId, Date startDate, Date endDate);
    
    public List<EpdIedIela> getZoneEnergyByDate(String zone, Date startDate, Date endDate);
    
    public List<EpdIedIela> getMunicipalityEnergyByDate(String municipality, Date startDate, Date endDate);
}
