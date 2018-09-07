package net.wedjaa.wetnet.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import net.wedjaa.wetnet.business.domain.KMLType;
import net.wedjaa.wetnet.business.services.MapViewService;
import net.wedjaa.wetnet.web.spring.model.ResultMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * controller per componenti generiche
 * 
 * @author alessandro vincelli, massimo ricci
 *
 */
@Controller
@RequestMapping("/misc")
//@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
public class MiscController {

    protected static final Logger logger = Logger.getLogger(MiscController.class.getName());

    @Autowired
    private MessageSource messages;
    @Autowired
    private MapViewService mapViewService;
    
    @RequestMapping(value = "/user-password-modify", method = RequestMethod.GET)
    public String users(ModelMap model) {
        return "wetnet/misc/user-password-modify";
    }
    
    @RequestMapping(value = "/map-view", method = RequestMethod.GET)
    public String mapView(ModelMap model) {
        return "wetnet/misc/map-view";
    }
    
    @RequestMapping(value = "/map-view-ol3", method = RequestMethod.GET)
    public String mapViewOl3(ModelMap model) {
        return "wetnet/misc/map-view-ol3";
    }
    
    @RequestMapping(value = "/map-view-ol2", method = RequestMethod.GET)
    public String mapViewOl2(ModelMap model) {
        return "wetnet/misc/map-view-ol2";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR', 'ROLE_OPERATOR'})")
    @RequestMapping(value = "/epanet", method = RequestMethod.GET)
    public String epanet(ModelMap model) {
        return "wetnet/misc/epanet";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/kml-upload", method = RequestMethod.GET)
    public String kmlUpload(ModelMap model) {
        return "wetnet/misc/kml-upload";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/areal-upload", method = RequestMethod.POST)
    public String arealUpload(ModelMap model, HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {     
        return handleFileUpload(model, request, file, KMLType.AREAL);
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/linear-upload", method = RequestMethod.POST)
    public String linearUpload(ModelMap model, HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {     
        return handleFileUpload(model, request, file, KMLType.LINEAR);
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/punctual-upload", method = RequestMethod.POST)
    public String punctualUpload(ModelMap model, HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {     
        return handleFileUpload(model, request, file, KMLType.PUNCTUAL);
    }
    
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getInfoPage(ModelMap model) {
        return "wetnet/misc/info";
    }
    
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String getContactsPage(ModelMap model) {
        return "wetnet/misc/contacts";
    }
    
    private String handleFileUpload(ModelMap model, HttpServletRequest request, @RequestParam("file") MultipartFile file, KMLType type) throws Exception {
        String result = mapViewService.updateKML(file, type);
        model.addAttribute("msg", new ResultMessage(result, messages.getMessage("kml-upload.form.alert." + result, null, request.getLocale())));    
        return "wetnet/misc/kml-upload";
    }
}
