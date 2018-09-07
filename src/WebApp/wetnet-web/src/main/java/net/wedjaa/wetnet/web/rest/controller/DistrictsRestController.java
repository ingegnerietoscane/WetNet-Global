package net.wedjaa.wetnet.web.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.commons.NumberUtil;
import net.wedjaa.wetnet.business.commons.PropertiesUtil;
import net.wedjaa.wetnet.business.dao.DistrictsBandsHistoryDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsBandsHistory;
import net.wedjaa.wetnet.business.domain.JSONResponse;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.services.DataDistrictsService;
import net.wedjaa.wetnet.security.Roles;
import net.wedjaa.wetnet.security.UserDetailsImpl;

import org.codehaus.plexus.logging.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
@Controller
@RequestMapping("/districts")
public class DistrictsRestController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private DistrictsDAO districtsDAO;
    
    @Autowired
    private DistrictsBandsHistoryDAO districtsBandsHistoryDAO;
    
    @Autowired
    private DataDistrictsService dataDistrictsService;
    
    @Autowired
    private PropertiesUtil propertiesSource;
    

    /**
     * 
     * @param response
     * @return
     */
    @RequestMapping(method = { RequestMethod.GET })
    public JSONResponse getAll(HttpServletResponse response, Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        
        if(Roles.isROLE_OPERATOR(details.getAuthorities()))
            jsonResponse.addData("districts", districtsDAO.getAllDistricts(details.getUsers()));
        else
            jsonResponse.addData("districts", districtsDAO.getAllDistricts());
        
        return jsonResponse;
    }

    /**
     * 
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = { RequestMethod.GET })
    public JSONResponse getById(HttpServletResponse response, @PathVariable("id") long id) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        Districts d = districtsDAO.getById(id);
        d.setHousehold_night_use(NumberUtil.roundToAnyDecimal(d.getHousehold_night_use(), 2));
        d.setEv_statistic_high_band(NumberUtil.roundToAnyDecimal(d.getEv_statistic_high_band(), 2));
        d.setEv_statistic_low_band(NumberUtil.roundToAnyDecimal(d.getEv_statistic_low_band(), 2));
        jsonResponse.addData("district", d);
        
        //***RC 04/12/2015***
        jsonResponse.addData("resetURL", propertiesSource.getProperty("district.reset.url"));
        jsonResponse.addData("resetURLparam1", propertiesSource.getProperty("district.reset.param1"));
        jsonResponse.addData("resetURLparam2", propertiesSource.getProperty("district.reset.param2"));
        jsonResponse.addData("resetURLparam3", propertiesSource.getProperty("district.reset.param3"));
        //***END***
        
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param districts
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST })
    public JSONResponse save(HttpServletRequest request, @RequestBody Districts districts) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("district", districtsDAO.saveOrUpdate(districts));

        //save bands history
        if (districts.getOld_ev_high_band() != districts.getEv_high_band()
                || districts.getOld_ev_low_band() != districts.getEv_low_band()){
            DistrictsBandsHistory districtsBandsHistory = new DistrictsBandsHistory(districts.getIdDistricts(), new Date(), districts.getEv_high_band(), districts.getEv_low_band());
            districtsBandsHistoryDAO.insertDistrictsBandsHistory(districtsBandsHistory);
        }
        
        jsonResponse.setMessage(messages.getMessage("district.form.save.alert.success", null, request.getLocale()));
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "{id}", method = { RequestMethod.DELETE })
    public JSONResponse delete(HttpServletRequest request, @PathVariable("id") long id) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.setMessage(messages.getMessage("district.form.delete.alert.success", null, request.getLocale()));
        districtsDAO.delete(id);
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param measures_id
     * @param districts_id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "/measures", method = { RequestMethod.POST })
    public JSONResponse addMeasures(HttpServletRequest request, @RequestParam("measures_id") long measures_id, @RequestParam("districts_id") long districts_id, @RequestParam("sign") long sign,
            @RequestParam("connections_id_odbcdsn") long connections_id_odbcdsn) {
        
        try{
            JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
            /* GC - 22/10/2015 */
            /*districtsDAO.addMeasures(districts_id, measures_id, sign, connections_id_odbcdsn);*/
            districtsDAO.addMeasures(districts_id, measures_id, sign);

            jsonResponse.setMessage(messages.getMessage("measure.form.addMeasures.alert.success", null, request.getLocale()));
            return jsonResponse;
        }
        catch (DuplicateKeyException duplicateKeyException) {
            JSONResponse jsonResponse = new JSONResponse(JSONResponse.FAIL);
            jsonResponse.setMessage(messages.getMessage("measure.form.addMeasures.alert.alreadyAddedd", null, request.getLocale()));
            return jsonResponse;
        }

    }

    /**
     * 
     * @param request
     * @param districts_id
     * @return
     */
    @RequestMapping(value = "/measures", method = { RequestMethod.GET })
    public JSONResponse getMesures(HttpServletRequest request, @RequestParam("districts_id") long districts_id) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("measures", districtsDAO.getMeasuresHasDistrictsByDistrictsId(districts_id));
        jsonResponse.setMessage(messages.getMessage("user.form.addDistricts.alert.success", null, request.getLocale()));
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param measures_id
     * @param districts_id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "/measures", method = { RequestMethod.DELETE })
    public JSONResponse deleteMesures(HttpServletRequest request, @RequestParam("measures_id") long measures_id, @RequestParam("districts_id") long districts_id) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        districtsDAO.removeMeasuresHasDistricts(measures_id, districts_id);
        jsonResponse.setMessage(messages.getMessage("measure.form.removeMeasures.alert.success", null, request.getLocale()));
        return jsonResponse;
    }

    /**
     * Distretto vuoto con date inizializzate, utilizato dalla view per l'inserimento un nuovo distretto 
     * 
     * 
     * @return
     */
    @RequestMapping(value = "/emptyDistrict", method = { RequestMethod.GET })
    public JSONResponse emptyDistricts(HttpServletRequest request) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("district", createADistricts());
        return jsonResponse;
    }

    private Districts createADistricts() {
        Date date = new DateTime(1900, 1, 1, 0, 0, 0, 0).toDate();
        Districts d = new Districts();
        d.setUpdate_timestamp(date);
        d.setMin_night_start_time(date);
        d.setMin_night_stop_time(date);
       
        /*
         * GC 22/10/2015
         */
        /*
        d.setMax_day_start_time_2(date);
        d.setMax_day_start_time_3(date);
        d.setMax_day_stop_time_1(date);
        d.setMax_day_stop_time_2(date);
        d.setMax_day_stop_time_3(date);
        */
        d.setdClass((long) 0);
        //d.setEv_last_good_sample_day(date);
        return d;
    }
    
  //***RC 25/11/2015***
    /**
     * 
     * @param response
     * @param measures
     * 
     * @return csv
     * @throws IOException 
     */
    //@Secured()
    @RequestMapping(value = "/csv",  method = { RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getDistrictsListCSV(HttpServletRequest request,HttpServletResponse response, @RequestBody List<Districts> districts) throws IOException {
        String csv = dataDistrictsService.createCSVFromListD(districts,request.getLocale());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    //***END***
    
    //***RC 27/11/2015***
    /**
     * 
     * @return 
     */
    @RequestMapping(value = "/getIpPort",  method = {RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String getPropertiesIpPort(HttpServletResponse response) {
        String ipPort = "127.0.0.1:2314";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return ipPort;
    }
    //***END***
    
    //***RC TEMPORANEO***
    /**
     * 
     * @return 
     */
    @RequestMapping(value = "/ResetDistrictData",  method = {RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String doResetDistrict(HttpServletResponse response, @RequestParam("url") String url) {
    	 	
		URL obj;
		
		try {
			obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer result = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			result.append(inputLine);
		}
		in.close();

		//print result
		
		return result.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String xml = "<?xml version='1.0'?>"
	        		+ "<Return>0</Return>";
	        response.setContentType("text/xml");
	        response.setCharacterEncoding("UTF-8");
	        return xml;
		}
    }
    
    /**
     * 					METODO RICHIAMATO PER SIMULAZIONE $GET CROSS-DOMAIN PER RESET DISTRETTO
     * @return 
     */
    @RequestMapping(value = "/ResetDistrictDataFinal",  method = {RequestMethod.POST, RequestMethod.GET } )
    @ResponseBody
    public String doResetDistrictFinal(HttpServletResponse response) {
    	
        String xml = "<?xml version='1.0'?>"
        		+ "<Return>1</Return>";
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        return xml;
    }
    //***END***
}
