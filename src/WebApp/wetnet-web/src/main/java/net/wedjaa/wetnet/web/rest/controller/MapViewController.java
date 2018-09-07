package net.wedjaa.wetnet.web.rest.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.domain.KMLType;
import net.wedjaa.wetnet.business.services.MapViewService;
import net.wedjaa.wetnet.security.Roles;
import net.wedjaa.wetnet.security.UserDetailsImpl;

import org.apache.commons.io.IOUtils;
import org.geojson.GeoJsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * 
 * @author alessandro vincelli, massimo ricci
 */
@Controller
@RequestMapping(value = "/map-view")
public class MapViewController {

    @Autowired
    private MapViewService mapViewService;

    private static Logger log = LoggerFactory.getLogger(MapViewController.class);

    /**
     * layers per distretti in formato KLM (Google Heart)
     * 
     * @param response
     * @param 
     * 
     * @return klm data
     * @throws IOException 
     */
    @RequestMapping(value = "/distretti.kml", method = { RequestMethod.GET })
    public void getKLM(HttpServletResponse response) throws IOException {
        String data= mapViewService.getDistrictsKLM();
        response.setContentType("application/vnd.google-earth.kml+xml");
        IOUtils.copy(IOUtils.toInputStream(data), response.getOutputStream());    
    }
    
    @RequestMapping(value = "/districts.kml", method = { RequestMethod.GET })
    public void getDistrictsLayer(HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        String data = null;
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            data= mapViewService.getDistrictsLayer(details.getUsers());
        else
            data= mapViewService.getDistrictsLayer(null);
        
        response.setContentType("application/vnd.google-earth.kml+xml");
        IOUtils.copy(IOUtils.toInputStream(data), response.getOutputStream());    
    }
    
    @RequestMapping(value = "/measures.kml", method = { RequestMethod.GET })
    public void getMeasuresLayer(HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        String data = null;
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            data= mapViewService.getMeasuresLayer(details.getUsers());
        else
            data= mapViewService.getMeasuresLayer(null);
        
        response.setContentType("application/vnd.google-earth.kml+xml");
        IOUtils.copy(IOUtils.toInputStream(data), response.getOutputStream());    
    }
    
    @RequestMapping(value = "/measures.geojson", method = { RequestMethod.GET })
    public GeoJsonObject getMeasures(HttpServletResponse response) throws IOException {
        GeoJsonObject data= mapViewService.getMeasuresGeoJSON();
        return data;
    }
    
    @RequestMapping(value = "/areal-kml", method = { RequestMethod.GET })
    public void getArealKml(HttpServletResponse response) throws IOException {
        String data= mapViewService.getKML(KMLType.AREAL.getValue());
        response.setContentType("application/vnd.google-earth.kml+xml");
        IOUtils.copy(IOUtils.toInputStream(data), response.getOutputStream());    
    }
    
    @RequestMapping(value = "/linear-kml", method = { RequestMethod.GET })
    public void getLinearKml(HttpServletResponse response) throws IOException {
        String data= mapViewService.getKML(KMLType.LINEAR.getValue());
        response.setContentType("application/vnd.google-earth.kml+xml");
        IOUtils.copy(IOUtils.toInputStream(data), response.getOutputStream());    
    }
    
    @RequestMapping(value = "/punctual-kml", method = { RequestMethod.GET })
    public void getPunctualKml(HttpServletResponse response) throws IOException {
        String data= mapViewService.getKML(KMLType.PUNCTUAL.getValue());
        response.setContentType("application/vnd.google-earth.kml+xml");
        IOUtils.copy(IOUtils.toInputStream(data), response.getOutputStream());    
    }
}
