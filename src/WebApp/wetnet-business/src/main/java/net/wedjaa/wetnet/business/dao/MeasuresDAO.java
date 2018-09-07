package net.wedjaa.wetnet.business.dao;

import java.util.List;

import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.MeasuresMeterReading;
import net.wedjaa.wetnet.business.domain.MeasuresWithSign;
import net.wedjaa.wetnet.business.domain.Users;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public interface MeasuresDAO {

    /**
     * restituisce tutte le misure
     * 
     * @return
     */
    public List<Measures> getAll();
    
    /**
     * restituisce tutte le misure visibili all'utente dato
     * 
     * @param user
     * @return
     */
    List<Measures> getAll(Users user);

    /**
     * restituisce le misure di un distretto
     * 
     * @param districtId
     * @return
     */
    public List<Measures> getByDistrictId(long districtId);
    
    
    /** GC - 29/10/2015
     * restituisce le misure di un distretto con il segno dell'associazione
     * 
     * @param districtId
     * @return
     */
    public List<MeasuresWithSign> getWithSignByDistrictId(long districtId);

    /**
     * 
     * @param measures
     * @return
     */
    public Measures saveOrUpdate(Measures measures);

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
    public Measures getById(long id);

    /**
     * 
     * @param id
     * @return
     */
    public List<MeasuresMeterReading> getAllMeterReadingByMeasure(long id);
    
    /**
     * 
     * @param id
     * @return
     */
    public MeasuresMeterReading getLastMeterReadingByMeasure(long id);
    
    /**
     * 
     * @param measures
     * @return
     */
    public MeasuresMeterReading saveMeterReading(MeasuresMeterReading measures);

}
