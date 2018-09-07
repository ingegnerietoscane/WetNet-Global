package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;

import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsBandsHistory;
import net.wedjaa.wetnet.business.domain.MeasuresHasDistricts;
import net.wedjaa.wetnet.business.domain.Users;

/**
 * @author massimo ricci
 * @author alessandro vincelli
 *
 */
public interface DistrictsDAO {

    /**
     * 
     * @return
     */
    public List<Districts> getAllDistricts();
    
    /**
     * 
     * @return
     */
    public List<Districts> getAllDistricts(Users users);

    /**
     * 
     * @param districts
     * @return
     */
    public Districts saveOrUpdate(Districts districts);

    /**
     * 
     * @param id_districts
     * @param id_measures
     * @param sign
     * @param connections_id_odbcdsn
     * 
     */
    /* GC - 22/10/2015 */
    /*public void addMeasures(long id_districts, long id_measures, long sign, long connections_id_odbcdsn);*/
      public void addMeasures(long id_districts, long id_measures, long sign);



    /**
     * MeasuresHasDistricts di un distretto
     * 
     * @param id_districts
     * @return lista di MeasuresHasDistricts
     */
    public List<MeasuresHasDistricts> getMeasuresHasDistrictsByDistrictsId(long id_districts);

    /**
     * 
     * @param id
     */
    public void delete(long id);

    /**
     * 
     * @param id
     * @return
     */
    public Districts getById(long id);

    /**
     * 
     * @param measures_id
     * @param districts_id
     */
    public void removeMeasuresHasDistricts(long measures_id, long districts_id);

    public List<Districts> getAllZones(Users user);
    
    public List<Districts> getAllMunicipalities(Users user);
    
    //***RC 04/11/2015***
    public List<Districts> getAllWaterAuthorities(Users user);
    //***END***

    /*** GC 12/11/2015*/
	public List<DistrictsBandsHistory> getAllDistrictsBandsHistory(long id);

	 /*** GC 13/11/2015*/
		public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictonDays(Date startDate, Date endDate, long idDistricts);
		public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictAll(Date startDate, Date endDate, long idDistricts);
		public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictonHours(Date startDate, Date endDate, long idDistricts);
		public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictonMonths(Date startDate, Date endDate, long idDistricts);
		public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictonYears(Date startDate, Date endDate, long idDistricts);
		public List<DistrictsBandsHistory> getBandsHistoryByDateDistrictAVG(Date startDate, Date endDate, long idDistricts);
		
		
		/* GC 16/02/2016*/
		public List<DistrictsBandsHistory> getFirstBandsHistoryByDistrictsOnTimestampAsc(Date startDate, Date endDate, long idDistricts);

		public List<DistrictsBandsHistory> getLastBandsHistoryByDistrictsOnTimestampDesc(Date startDate, Date endDate,
				long idDistricts);
}
