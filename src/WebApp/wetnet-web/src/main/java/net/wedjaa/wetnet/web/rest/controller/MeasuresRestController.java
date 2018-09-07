package net.wedjaa.wetnet.web.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.dao.MeasuresDAO;
import net.wedjaa.wetnet.business.domain.Alarms;
import net.wedjaa.wetnet.business.domain.JSONResponse;
import net.wedjaa.wetnet.business.domain.Measures;
import net.wedjaa.wetnet.business.domain.MeasuresMeterReading;
import net.wedjaa.wetnet.security.Roles;
import net.wedjaa.wetnet.security.UserDetailsImpl;
import net.wedjaa.wetnet.business.services.DataDistrictsService;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
@Controller
@RequestMapping("/measures")
public class MeasuresRestController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private MeasuresDAO measuresDAO;
    
    @Autowired
    private DataDistrictsService dataDistrictsService;

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
            jsonResponse.addData("measures", measuresDAO.getAll(details.getUsers()));
        else
            jsonResponse.addData("measures", measuresDAO.getAll());
            
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
        jsonResponse.addData("measure", measuresDAO.getById(id));
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param measures
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST })
    public JSONResponse save(HttpServletRequest request, @RequestBody Measures measures) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("measure", measuresDAO.saveOrUpdate(measures));
        jsonResponse.setMessage(messages.getMessage("measure.form.save.alert.success", null, request.getLocale()));
        return jsonResponse;
    }

    /**
     * Misura vuota con date inizializzate, utilizato dalla view per l'inserimento una nuova misura 
     * 
     * 
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "/emptyMeasure", method = { RequestMethod.GET })
    public JSONResponse emptyMeasures(HttpServletRequest request) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("measure", createMeasures());
        return jsonResponse;
    }

    private Measures createMeasures() {
        Date date = new DateTime(1900, 1, 1, 0, 0, 0, 0).toDate();
        Measures m = new Measures();
        m.setMin_night_start_time(date);
        m.setMin_night_stop_time(date);
        
        /* GC - 22/10/2015 */
        /*
        m.setMax_day_start_time_1(date);
        m.setMax_day_start_time_2(date);
        m.setMax_day_start_time_3(date);
        m.setMax_day_stop_time_1(date);
        m.setMax_day_stop_time_2(date);
        m.setMax_day_stop_time_3(date);
        */
        return m;
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
    public String getMeasuresListCSV(HttpServletRequest request,HttpServletResponse response, @RequestBody List<Measures> measures) throws IOException {
        String csv = dataDistrictsService.createCSVFromListM(measures,request.getLocale());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return csv;
    }
    //***END***
    
    
    
    @RequestMapping(value = "/measuresMeterReading/{id}", method = { RequestMethod.GET })
    public List<MeasuresMeterReading> measuresMeterReadingAll(HttpServletRequest request,Authentication authentication,@PathVariable("id") long id) {
    	 UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
    	// System.out.println("id users in get ALL" + details.getIdusers());
       return measuresDAO.getAllMeterReadingByMeasure(id);
    }
    
    @RequestMapping(value = "/measuresMeterReading", method = {RequestMethod.POST })
    public JSONResponse saveMeterReading(HttpServletRequest request, Authentication authentication,@RequestBody MeasuresMeterReading measuresMeterReading) {
    	UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
    	measuresMeterReading.setIdUserreader(details.getIdusers());
    	JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("measuresMeterReading", measuresDAO.saveMeterReading(measuresMeterReading));
        jsonResponse.setMessage(messages.getMessage("measureReading.form.save.alert.success", null, request.getLocale()));
        return jsonResponse;
    }
    
}
