/**
 * 
 */
package net.wedjaa.wetnet.web.rest.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import net.wedjaa.wetnet.business.services.InfoService;
import net.wedjaa.wetnet.web.spring.model.WetnetInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Massimo Ricci
 * @date Feb 10, 2015
 */
@Controller
@RequestMapping("/info")
public class InfoRestController {

    private static final Logger logger = Logger.getLogger(EventsRestController.class.getName());

    @Autowired
    private InfoService infoService;
    
    /**
     * 
     * @param response
     * @return le informazioni su WetNet
     */
    //@Secured()
    @RequestMapping(value = "/version", method = { RequestMethod.GET })
    public WetnetInfo getWetnetVersion(HttpServletRequest request) {
        logger.info("received request for 'getWetnetVersion'");
        return infoService.getWetnetInfo();
    }
    
}
