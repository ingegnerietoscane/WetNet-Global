package net.wedjaa.wetnet.web.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.dao.EventsDAO;
import net.wedjaa.wetnet.business.domain.Alarms;
import net.wedjaa.wetnet.business.domain.AlarmsData;
import net.wedjaa.wetnet.business.domain.CatChartData;
import net.wedjaa.wetnet.business.domain.Events;
import net.wedjaa.wetnet.business.domain.EventsData;
import net.wedjaa.wetnet.business.domain.GanttChartData;
import net.wedjaa.wetnet.business.domain.PieChartData;
import net.wedjaa.wetnet.business.domain.ScatterChartData;
import net.wedjaa.wetnet.business.services.AlarmsService;
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
 * @author graziella cipolletti
 *
 */
@Controller
@RequestMapping("/alarms")
public class AlarmsRestController {

    private static final Logger logger = Logger.getLogger(AlarmsRestController.class.getName());

     
    @Autowired
    private AlarmsService alarmsService;
    
    /**
     * 
     * @param response
     * @return
     */
    //@Secured()
    @RequestMapping(value = "/data", method = { RequestMethod.POST })
    public List<Alarms> getAlarmsJSON(HttpServletResponse response, @RequestBody AlarmsData alarmData, Authentication authentication) {
        logger.info("received request for 'getAlarmsJSON'");
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        
        List<Alarms> result = null;
        if(alarmData.isClosedAlarms())
        {
        	if(Roles.isROLE_OPERATOR(details.getAuthorities()))
	            result = alarmsService.getAlarmsClose(alarmData,details.getUsers());
	        else
	            result = alarmsService.getAlarmsClose(alarmData,null);
        }
        else
        {
          if(Roles.isROLE_OPERATOR(details.getAuthorities()))
	            result = alarmsService.getAlarms(alarmData,details.getUsers());
	        else
	            result = alarmsService.getAlarms(alarmData,null);
        }
       return result;
    }
    
    
    @RequestMapping(value = "/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getAlarmsCSV(HttpServletResponse response, HttpServletRequest request, @RequestBody List<Alarms> dataList) {
        String csv = alarmsService.getAlarmsDataCSV(dataList, request.getLocale());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    
}
