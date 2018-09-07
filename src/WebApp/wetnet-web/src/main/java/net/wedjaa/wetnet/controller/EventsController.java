package net.wedjaa.wetnet.controller;

import java.security.Principal;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
@Controller
@RequestMapping("/events")
public class EventsController {

    private static final Logger logger = Logger.getLogger(EventsController.class.getName());
    
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String getGraphicG3_2(ModelMap model, Principal principal) {
        return "wetnet/events/events";
    }
    
    @RequestMapping(value = "/events-pie-chart", method = RequestMethod.GET)
    public String getEventsPieChart(ModelMap model, Principal principal) {
        return "wetnet/events/events-pie-chart";
    }

    @RequestMapping(value = "/events-scatter-chart", method = RequestMethod.GET)
    public String getEventsScatterChart(ModelMap model, Principal principal) {
        return "wetnet/events/events-scatter-chart";
    }
    
    @RequestMapping(value = "/events-cat-chart", method = RequestMethod.GET)
    public String getEventsCatChart(ModelMap model, Principal principal) {
        return "wetnet/events/events-cat-chart";
    }  
    
    @RequestMapping(value = "/events-gantt-chart", method = RequestMethod.GET)
    public String getEventsGanttChart(ModelMap model, Principal principal) {
        return "wetnet/events/events-gantt-chart";
    }
}
