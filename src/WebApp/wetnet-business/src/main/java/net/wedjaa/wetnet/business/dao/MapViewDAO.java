package net.wedjaa.wetnet.business.dao;

import java.util.List;

import net.wedjaa.wetnet.business.domain.MapView;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public interface MapViewDAO {

    /**
     * 
     * 
     * @return
     */
    public List<MapView> getAll();
    
    public MapView getKML(int id);

    public MapView getLayer(long id);
    
    public MapView update(MapView obj);
    
    /**
     * 
     * @param mapView
     * @return
     */
    public MapView saveOrUpdate(MapView mapView);

    /**
     * 
     * @param id
     */
    public void delete(long id);

}
