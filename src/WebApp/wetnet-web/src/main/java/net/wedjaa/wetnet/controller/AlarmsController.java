package net.wedjaa.wetnet.controller;

import java.security.Principal;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author graziella cipolletti, roberto cascelli
 *
 */
@Controller
@RequestMapping("/alarms")
public class AlarmsController {

    @RequestMapping(value = "/alarms-active", method = RequestMethod.GET)
    public String getActiveAlarms(ModelMap model, Principal principal) {
        return "wetnet/alarms/alarms-active";
    }
    
    @RequestMapping(value = "/alarms-history", method = RequestMethod.GET)
    public String getAlarmsHistory(ModelMap model, Principal principal) {
        return "wetnet/alarms/alarms-history";
    }
    
   
}
