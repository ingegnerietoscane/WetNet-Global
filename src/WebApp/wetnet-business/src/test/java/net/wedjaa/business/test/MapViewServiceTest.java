/**
 * 
 */
package net.wedjaa.business.test;

import static org.junit.Assert.assertNotNull;
import net.wedjaa.wetnet.business.services.MapViewService;

import org.geojson.GeoJsonObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author massimo ricci
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-business.xml" })
public class MapViewServiceTest {
    
    private final static Logger logger = LoggerFactory.getLogger(MapViewServiceTest.class);

    @Autowired
    private MapViewService mapViewService;
    
    @Test
    @Ignore
    public void getLayerTest(){
        String layerChanged = mapViewService.getDistrictsLayer(null);
        assertNotNull("Layer is null", layerChanged);
    }
    
    @Test
    @Ignore
    public void getDistrictsGeoJSONTest(){
        GeoJsonObject obj = mapViewService.getDistrictsGeoJSON(null);
        assertNotNull("Layer is null", obj);
    }
}
