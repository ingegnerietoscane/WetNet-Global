package net.wedjaa.wetnet.web.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.dao.EventsDAO;
import net.wedjaa.wetnet.business.domain.CatChartData;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsData;
import net.wedjaa.wetnet.business.domain.GanttChartData;
import net.wedjaa.wetnet.business.domain.PieChartData;
import net.wedjaa.wetnet.business.domain.ScatterChartData;
import net.wedjaa.wetnet.business.services.EventsService;
import net.wedjaa.wetnet.security.Roles;
import net.wedjaa.wetnet.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
@Controller
@RequestMapping("/events")
public class EventsRestController {

    private static final Logger logger = Logger.getLogger(EventsRestController.class.getName());

    @Autowired
    private EventsDAO eventsDAO;
    
    @Autowired
    private EventsService eventService;
    
    /**
     * 
     * @param response
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/data", method = { RequestMethod.POST })
    public List<Events> getEventsJSON(HttpServletResponse response, @RequestBody EventsData eventData, Authentication authentication) {
        logger.info("received request for 'getEventsJSON'");
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        List<Events> result = null;
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            result = eventService.getEventsByIdDate(eventData, details.getUsers());
        else
            result = eventService.getEventsByIdDate(eventData, null);
        
        return result;
    }
    
    /**
     * 
     * @param request
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/pie-chart", method = { RequestMethod.POST })
    public PieChartData getEventsPieChart(HttpServletRequest request, @RequestBody EventsData eventData) {
        logger.info("received request for 'getEventsPieChart'");
        PieChartData result = eventService.getEventsPieChartData(eventData, request.getLocale());     
        return result;
    }
    
    /**
     * 
     * @param response
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/scatter-chart", method = { RequestMethod.POST })
    public ScatterChartData getEventsScatterChart(HttpServletRequest request, @RequestBody EventsData eventData) {
        logger.info("received request for 'getEventsScatterChart'");
        ScatterChartData result = eventService.getEventsScatterChartData(eventData, request.getLocale());     
        return result;
    }
    
    /**
     * 
     * @param response
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/gantt-chart", method = { RequestMethod.POST })
    public GanttChartData getEventsGanttChart(HttpServletRequest request, @RequestBody EventsData eventData) {
        logger.info("received request for 'getEventsGanttChart'");    
        GanttChartData result = eventService.getEventsGanttChartData(eventData, request.getLocale());       
        return result;
    }
    
    /**
     * 
     * @param response
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/cat-chart", method = { RequestMethod.POST })
    public CatChartData getEventsCatChart(HttpServletRequest request, @RequestBody EventsData eventData, Authentication authentication) {
        logger.info("received request for 'getEventsCatChart'");
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        CatChartData result = null;
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            result = eventService.getEventsCatChartData(eventData, request.getLocale(), details.getUsers());  
        else
            result = eventService.getEventsCatChartData(eventData, request.getLocale(), null); 
        
        return result;
    }
    
    /**
     * All zones
     * 
     * @param response
     * @return 
     */
    //@Secured()
    @RequestMapping(value = "/zone")
    public List<String> getZonesJSON(Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        List<String> result = new ArrayList<String>();
        
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            result = eventService.getAllZones(details.getUsers());
        else
            result = eventService.getAllZones(null);

        return result;
    }
    
    /**
     * All zones
     * 
     * @param response
     * @return 
     */
    //@Secured()
    @RequestMapping(value = "/municipality")
    public List<String> getMunicipalitiesJSON(Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        List<String> result = new ArrayList<String>();
        
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            result = eventService.getAllMunicipalities(details.getUsers());
        else
            result = eventService.getAllMunicipalities(null);

        return result;
    }
    
    //***RC 04/11/2015***
    /**
     * All waterAuthorities
     * 
     * @param response
     * @return 
     */
    //@Secured()
    @RequestMapping(value = "/waterAuthority")
    public List<String> getWaterAuthoritiesJSON(Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        List<String> result = new ArrayList<String>();
        
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            result = eventService.getAllWaterAuthorities(details.getUsers());
        else
            result = eventService.getAllWaterAuthorities(null);

        return result;
    }
    //***END***
    
    @RequestMapping(value = "/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getEventsCSV(HttpServletResponse response, HttpServletRequest request, @RequestBody List<Events> dataList) {
        String csv = eventService.getEventsDataCSV(dataList, request.getLocale());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
}
