package net.wedjaa.wetnet.business.services;

import java.util.List;

import net.wedjaa.wetnet.business.domain.KMLType;
import net.wedjaa.wetnet.business.domain.MapView;
import net.wedjaa.wetnet.business.domain.Users;

import org.geojson.GeoJsonObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public interface MapViewService {

    String getDistrictsKLM();
    
    public String getDistrictsLayer(Users users);
    
    public String getMeasuresLayer(Users users);
    
    GeoJsonObject getMeasuresGeoJSON();
    
    public GeoJsonObject getDistrictsGeoJSON(Users users);

    public List<MapView> getAllKML();
    
    public String getKML(int id);
    
    public String updateKML(MultipartFile file, KMLType type);
}
