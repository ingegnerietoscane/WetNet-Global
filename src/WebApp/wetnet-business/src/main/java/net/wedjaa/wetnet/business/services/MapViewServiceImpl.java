package net.wedjaa.wetnet.business.services;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import net.wedjaa.wetnet.business.commons.KMLUtil;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.dao.MapViewDAO;
import net.wedjaa.wetnet.business.dao.MeasuresDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.KMLType;
import net.wedjaa.wetnet.business.domain.MapView;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.web.spring.model.ResultMessage;

import org.apache.commons.io.IOUtils;
import org.geojson.Crs;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public class MapViewServiceImpl implements MapViewService {

    private static final Logger logger = Logger.getLogger("MapViewServiceImpl");
    
    @Autowired
    private MapViewDAO mapViewDAO;
    @Autowired
    private DistrictsDAO districtsDAO;
    @Autowired
    private MeasuresDAO measuresDAO;

    @PostConstruct
    public void initApp() {
//        MapView mapView = mapViewDAO.get();
//        if (mapView == null || StringUtils.isBlank(mapView.getKml())) {
//            mapView = new MapView();
//            String string;
//            try {
//                string = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("districts.kml"));
//                mapView.setKml(string);
//            } catch (IOException e) {
//                logger.log(Level.SEVERE, e.getMessage());
//            }
//            mapViewDAO.saveOrUpdate(mapView);
//        }
    }

    @Override
    @Deprecated
    public String getDistrictsKLM() {
        return mapViewDAO.getAll().get(0).getKml();
    }
    
    @Override
    @Deprecated
    public String getDistrictsLayer(Users users) {
        
        List<Districts> districts = districtsDAO.getAllDistricts(users);
        
        //Lista id dei distretti associati all'utente
        List<Long> ids = new ArrayList<Long>();
        for (Districts d : districts){
            ids.add(d.getIdDistricts());
        }
        
        String layer = mapViewDAO.getLayer(5).getKml();
        
        Kml kml = Kml.unmarshal(layer);
        Feature featureDocument = kml.getFeature();
        List<Feature> placemarkList = KMLUtil.getPlacemarkList(featureDocument);
        
        Iterator<Feature> iterator = placemarkList.iterator();
        while (iterator.hasNext()){
            Feature f = iterator.next();
            if (f instanceof Placemark){
                Placemark p = (Placemark) f;
                String desc = (p.getDescription() != null) ? p.getDescription().trim().substring(2) : "";
                long idDistricts = Long.parseLong(desc);
                if (!ids.contains(idDistricts)){
                    iterator.remove();
                }
            }
        }
        
        Writer strWriter = new StringWriter();
        kml.marshal(strWriter);
        return strWriter.toString();
    }
    
    @Override
    @Deprecated
    public String getMeasuresLayer(Users users) {
        
        List<Measures> measures = measuresDAO.getAll(users);
        
        //Lista id delle misure associate all'utente
        List<Long> ids = new ArrayList<Long>();
        for (Measures m : measures){
            ids.add(m.getIdMeasures());
        }
        
        String layer = mapViewDAO.getLayer(6).getKml();
        
        Kml kml = Kml.unmarshal(layer);
        Feature featureDocument = kml.getFeature();
        List<Feature> placemarkList = KMLUtil.getPlacemarkList(featureDocument);
        
        Iterator<Feature> iterator = placemarkList.iterator();
        while (iterator.hasNext()){
            Feature f = iterator.next();
            if (f instanceof Placemark){
                Placemark p = (Placemark) f;
                String desc = (p.getDescription() != null) ? p.getDescription().trim().substring(2) : "";
                long idDistricts = Long.parseLong(desc);
                if (!ids.contains(idDistricts)){
                    iterator.remove();
                }
            }
        }
        
        Writer strWriter = new StringWriter();
        kml.marshal(strWriter);
        return strWriter.toString();
    }

    @Override
    @Deprecated
    public GeoJsonObject getMeasuresGeoJSON() {
        FeatureCollection featureCollection = new FeatureCollection();
        List<Measures> all = measuresDAO.getAll();
        for (Measures measures : all) {
            if (measures.getX_position() != 0 || measures.getY_position() != 0) {
                org.geojson.Feature feature = new org.geojson.Feature();
                HashMap<String, Object> fProp = new HashMap<String, Object>();
                fProp.put("name", measures.getName());
                fProp.put("idMeasures", measures.getIdMeasures());
                feature.setProperties(fProp);
                Point multiPoint = new Point(new LngLatAlt(measures.getX_position(), measures.getY_position()));
                feature.setGeometry(multiPoint);
                featureCollection.add(feature);
            }
        }

        Crs crs = new Crs();
        HashMap<String, Object> crsProp = new HashMap<String, Object>();
        crsProp.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
        crs.setProperties(crsProp);
        featureCollection.setCrs(crs);

        return featureCollection;
    }
    
    @Override
    @Deprecated
    public GeoJsonObject getDistrictsGeoJSON(Users users) {
        
        FeatureCollection featureCollection = new FeatureCollection();
        List<Districts> districts = districtsDAO.getAllDistricts(users);
        
        for (Districts d : districts){
            try {
                if (d.getMap() != null && !d.getMap().trim().equals("")){
                    FeatureCollection fc = new ObjectMapper().readValue(IOUtils.toInputStream(d.getMap(), "UTF-8"), FeatureCollection.class);
                    featureCollection.addAll((List<org.geojson.Feature>) fc.getFeatures());
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
           
        }
        
        return featureCollection;
    }
    
    @Override
    public List<MapView> getAllKML() {
        return mapViewDAO.getAll();
    }
    
    @Override
    public String getKML(int id) {
        String result = "";
        MapView mapView = mapViewDAO.getKML(id);
        if (mapView != null)
            result = mapView.getKml();
        return result;
    }
    
    @Override
    public String updateKML(MultipartFile file, KMLType type) {
        try {
            if (file != null && file.getBytes() != null && file.getBytes().length > 0) {
                
                String name = file.getOriginalFilename();
                if (name != null){
                    if (!name.toLowerCase().endsWith(".kml"))
                        return ResultMessage.WARNING;
                } else {
                    return ResultMessage.DANGER;
                }
                
                MapView uploadFile = new MapView();
                if (type == KMLType.AREAL){
                    uploadFile.setId_map_view(1);
                } else if (type == KMLType.LINEAR){
                    uploadFile.setId_map_view(2);
                } else if (type == KMLType.PUNCTUAL){
                    uploadFile.setId_map_view(3);
                }
                    
                uploadFile.setKml(IOUtils.toString(file.getBytes(), "UTF-8"));
                //mapViewDAO.update(uploadFile);
                mapViewDAO.saveOrUpdate(uploadFile);
                return ResultMessage.SUCCESS;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return ResultMessage.DANGER;
    }
}
