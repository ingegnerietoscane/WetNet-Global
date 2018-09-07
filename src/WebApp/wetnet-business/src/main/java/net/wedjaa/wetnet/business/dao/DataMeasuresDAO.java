package net.wedjaa.wetnet.business.dao;

import java.util.Date;
import java.util.List;

import net.wedjaa.wetnet.business.domain.DataMeasures;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public interface DataMeasuresDAO {

    /**
     * restituisce le data_misure filtrate per data e id misura, i dati sono raggruppati per giorno
     * 
     * @return
     */
    List<DataMeasures> getAVGOnDays(Date startDate, Date endDate, long idMeasures);

    /**
     * restituisce le data_misure filtrate per data e id misura
     * 
     * @param startDate
     * @param endDate
     * @param idMeasures
     * @return
     */
    List<DataMeasures> getAllFiltered(Date startDate, Date endDate, long idMeasures);

    /**
     * restituisce le data_misure filtrate per data e id misura, i dati sono raggruppati per ora
     * 
     * @param startDate
     * @param endDate
     * @param idMeasures
     * @return
     */
    List<DataMeasures> getAVGOnHours(Date startDate, Date endDate, long idMeasures);

    
    /** GC 03/11/2015
     * media
     * 
     * @param startDate
     * @param endDate
     * @param idMeasures
     * @return
     */
	List<DataMeasures> getAVG(Date startDate, Date endDate, long idMeasures);
	
	
	 /** GC 18/11/2015
     * restituisce le data_misure filtrate per data e id misura, i dati sono raggruppati per mese
     * 
     * @return
     */
    List<DataMeasures> getAVGOnMonths(Date startDate, Date endDate, long idMeasures);
    
    /**GC 18/11/2015
     * restituisce le data_misure filtrate per data e id misura, i dati sono raggruppati per anno
     * 
     * @return
     */
    List<DataMeasures> getAVGOnYears(Date startDate, Date endDate, long idMeasures);



}
