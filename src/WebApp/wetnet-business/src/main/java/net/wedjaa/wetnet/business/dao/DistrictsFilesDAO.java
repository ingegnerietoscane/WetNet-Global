package net.wedjaa.wetnet.business.dao;

import java.util.List;

import net.wedjaa.wetnet.business.domain.DistrictsFiles;

public interface DistrictsFilesDAO {

    public DistrictsFiles insert(DistrictsFiles obj);
    
    public List<DistrictsFiles> getAllByDistrict(long idDistricts);
    
    public DistrictsFiles getById(long id);
    
}
