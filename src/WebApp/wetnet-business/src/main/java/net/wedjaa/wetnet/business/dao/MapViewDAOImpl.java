package net.wedjaa.wetnet.business.dao;

import java.util.List;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.BusinessesException;
import net.wedjaa.wetnet.business.domain.MapView;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
public class MapViewDAOImpl implements MapViewDAO {

    private static final Logger logger = Logger.getLogger(MapViewDAOImpl.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<MapView> getAll() {
        return sqlSessionTemplate.selectList("mapView.getAll");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MapView getKML(int id) {
        return sqlSessionTemplate.selectOne("mapView.getKML", id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MapView getLayer(long id) {
        return sqlSessionTemplate.selectOne("mapView.getLayer", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapView saveOrUpdate(MapView obj) {
    	
        try {
            int result = 0;
            
            List<MapView> tableContent = getAll();
            if(tableContent.size() < 3){
            	
            	boolean isPresent = false;
            	
            	for(int i=0; i<tableContent.size(); i++){
            		
            		if(tableContent.get(i).getId_map_view() == obj.getId_map_view()){
            			
            			isPresent = true;
            		}
            	}
            	
            	if(isPresent){
            		
            		result = sqlSessionTemplate.update("mapView.update", obj);
            	}else{
            		
            		result = sqlSessionTemplate.insert("mapView.insert", obj);
            	}
            }else{
            	result = sqlSessionTemplate.update("mapView.update", obj);
            }
            /*
            if (obj.getId_map_view() > 0) {
                result = sqlSessionTemplate.update("mapView.update", obj);
            } else {
                result = sqlSessionTemplate.insert("mapView.insert", obj);
            }
            */
            if (result == 0) {
                logger.info("no row modified for " + ReflectionToStringBuilder.toString(obj));
            }
            return obj;
        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MapView update(MapView obj) {
        try {
            int result = sqlSessionTemplate.update("mapView.update", obj);

            if (result == 0) {
                logger.info("no row modified for " + ReflectionToStringBuilder.toString(obj));
            }
            return obj;
        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long idusers) {
        try {
            int result = sqlSessionTemplate.delete("mapView.delete", idusers);
            if (result == 0) {
                logger.info("no row deleted for id: " + idusers);
            }

        } catch (Exception e) {
            throw new BusinessesException(e);
        }
    }


}
