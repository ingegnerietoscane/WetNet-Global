package net.wedjaa.wetnet.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.wedjaa.wetnet.business.services.DataDistrictsService;



/**
 * 
 * controller per le pagine ci configurazione e amministrazione
 * 
 * @author alessandro vincelli, massimo ricci
 *
 */
@Controller
@RequestMapping("/manager")
//@PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
public class ManagerController {

    protected static final Logger logger = Logger.getLogger(ManagerController.class.getName());

    //***RC 06/11/2015***
  	@Autowired
      private DataDistrictsService dataDistrictsService;
  	@Autowired
      private MessageSource messages;
  	//***END*** 
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(ModelMap model, Principal principal) {
        return "wetnet/manager/users";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(ModelMap model, Principal principal) {
        return "wetnet/manager/user";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR', 'ROLE_OPERATOR'})")
    @RequestMapping(value = "/district", method = RequestMethod.GET)
    public String district(ModelMap model, Principal principal) {
        return "wetnet/manager/district";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR', 'ROLE_OPERATOR'})")
    @RequestMapping(value = "/districts", method = RequestMethod.GET)
    public String districts(ModelMap model, Principal principal) {
        return "wetnet/manager/districts";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR', 'ROLE_OPERATOR','ROLE_METER_READER'})")
    @RequestMapping(value = "/measure", method = RequestMethod.GET)
    public String measure(ModelMap model, Principal principal) {
    	 return "wetnet/manager/measure";
    }
    
    /*letture su misure*/
    @RequestMapping(value = "/measureMeterReading", method = RequestMethod.GET)
    public String measureMeterReading(ModelMap model, Principal principal) {
    	 return "wetnet/manager/measureMeterReading";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR', 'ROLE_OPERATOR','ROLE_METER_READER'})")
    @RequestMapping(value = "/measures", method = RequestMethod.GET)
    public String measures(ModelMap model, Principal principal) {
        return "wetnet/manager/measures";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/connections", method = RequestMethod.GET)
    public String connections(ModelMap model, Principal principal) {
        return "wetnet/manager/connections";
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/connection", method = RequestMethod.GET)
    public String connection(ModelMap model, Principal principal) {
        return "wetnet/manager/connection";
    }
    
    //***RC 06/11/2015***
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/districtUpload", method = RequestMethod.POST)
    public String districtFileUpload(ModelMap model, HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("hiddenId") String id, @RequestParam("fileDescription") String desc, @RequestParam("fileUri") String uri) throws Exception {    
    	return handleDistrictFileUpload(model, request, file, id, desc, uri);
    }
    
    private String handleDistrictFileUpload(ModelMap model, HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("hiddenId") String id, @RequestParam("fileDescription") String desc, @RequestParam("fileUri") String uri) throws Exception {
        String result = dataDistrictsService.updateDistrictFile(file, id, desc, uri);
        //model.addAttribute("msg", new ResultMessage(result, messages.getMessage("kml-upload.form.alert." + result, null, request.getLocale())));    			//***********MSG
        request.setAttribute("idRedirectDistricts", id);
        String url = "wetnet/manager/redirectDistricts";
        return url;			
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})")
    @RequestMapping(value = "/measureUpload", method = RequestMethod.POST)
    public String measureFileUpload(ModelMap model, HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("hiddenId") String id, @RequestParam("fileDescription") String desc, @RequestParam("fileUri") String uri) throws Exception {    
    	return handleMeasureFileUpload(model, request, file, id, desc, uri);
    }
    
    private String handleMeasureFileUpload(ModelMap model, HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("hiddenId") String id, @RequestParam("fileDescription") String desc, @RequestParam("fileUri") String uri) throws Exception {
        String result = dataDistrictsService.updateMeasureFile(file, id, desc, uri);
        //model.addAttribute("msg", new ResultMessage(result, messages.getMessage("kml-upload.form.alert." + result, null, request.getLocale())));    			//***********MSG
        request.setAttribute("idRedirectMeasures", id);
        String url = "wetnet/manager/redirectMeasures";
        return url;			
    }
    //***END***
}
