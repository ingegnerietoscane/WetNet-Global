/**
 * 
 */
package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;

import net.wedjaa.wetnet.business.domain.DayStatisticJoinDistrictsJoinEnergy;
import net.wedjaa.wetnet.business.domain.DayStatisticJoinMeasures;
import net.wedjaa.wetnet.business.domain.DistrictsDayStatistic;

/**
 * @author massimo ricci
 *
 */
public interface DistrictsDayStatisticDAO {

    @Deprecated
	public List<DistrictsDayStatistic> getAllDistrictsDayStatistic();
	
	public List<DistrictsDayStatistic> getDistrictsDayStatisticById(Date startDate, Date endDate, long idDistricts);
	
	public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergy(Date startDate, Date endDate, long idDistricts);
	
	public List<DistrictsDayStatistic> getDistrictsDayStatisticByDistrict(Date startDate, Date endDate, long idDistricts);
	
	public List<DistrictsDayStatistic> getDistrictsDayStatisticByMunicipality(Date startDate, Date endDate, String municipality);
	
	public List<DistrictsDayStatistic> getDistrictsDayStatisticByZone(Date startDate, Date endDate, String zone);

	
    /* GC 03/11/2015 */
	public List<DistrictsDayStatistic> getDistrictsDayStatisticAvgById(Date startDate, Date endDate, long idDistricts);

	 /* GC 04/11/2015 */
	public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergyAvg(Date startDate,
			Date endDate, long idDistricts);

	/* GC 16/11/2015*/
	public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergyAVG(Date startDate,
			Date endDate, long idDistricts);

	
	/* GC 18/11/2015*/
	public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergyonMonths(Date startDate, Date endDate, long idDistricts);
	public List<DayStatisticJoinDistrictsJoinEnergy> getDayStatisticJoinDistrictsJoinEnergyonYears(Date startDate, Date endDate, long idDistricts);
	
}
