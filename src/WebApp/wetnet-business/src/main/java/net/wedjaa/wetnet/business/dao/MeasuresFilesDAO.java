package net.wedjaa.wetnet.business.dao;

import java.util.List;

import net.wedjaa.wetnet.business.domain.DistrictsFiles;
import net.wedjaa.wetnet.business.domain.MeasuresFiles;

/**
 * @author roberto cascelli
 *
 */
public interface MeasuresFilesDAO {

    /**
     * 
     * 
     * @return
     */
    public MeasuresFiles insert(MeasuresFiles obj);
    
    public List<MeasuresFiles> getAllByMeasure(long idMeasures);
    
    public MeasuresFiles getById(long id);
}
