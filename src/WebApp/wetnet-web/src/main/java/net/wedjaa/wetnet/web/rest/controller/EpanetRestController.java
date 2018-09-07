package net.wedjaa.wetnet.web.rest.controller;

import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.commons.DateUtil;
import net.wedjaa.wetnet.business.domain.EpanetData;
import net.wedjaa.wetnet.business.domain.JSONResponse;
import net.wedjaa.wetnet.business.services.EpanetService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Provides some REST services for EPANET
 * 
 * @author alessandro vincelli, massimo ricci
 */
@Controller
@RequestMapping(value = "/epanet")
public class EpanetRestController {

    @Autowired
    private EpanetService epanetService;

    private static Logger log = LoggerFactory.getLogger(EpanetRestController.class);

    /**
     * File .PAT: testo contenente i valori del profilo di consumo del distretto selezionato nei giorni dati
     * 
     * @param response
     * @param data
     * 
     * @return PAT file
     */
    @RequestMapping(value = "/pat", method = { RequestMethod.POST, RequestMethod.GET })
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @ResponseBody
    public JSONResponse get_PAT(HttpServletResponse response, @RequestBody EpanetData data) {
        String csv = epanetService.get_PATFile(data);
//        response.setContentType("text/plain");
//        response.setCharacterEncoding("UTF-8");
        String nomeFile = data.getDistrictsSelected().getIdDistricts() + "_"+ DateUtil.SDF2SIMPLEUSA.print(data.getStartDate().getTime()) + "_" + data.getDistrictsSelected().getName() + ".pat";
        data.setResponseFileName(nomeFile);
        data.setResponseDataFile(csv);
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("data", data);
        return jsonResponse;
    }
    
    /**
     * File .DAT: contiene i valori di PRESSURE, FLOW, CARICO presi dalla tabelle measures_day_statistic per le misure selezionate.
     * 
     * @param response
     * @param data
     * 
     * @return PAT file
     */
    @RequestMapping(value = "/dat", method = { RequestMethod.POST, RequestMethod.GET })
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @ResponseBody
    public JSONResponse get_DAT(HttpServletResponse response, @RequestBody EpanetData data) {
        
        String csv = epanetService.get_DATFile(data);
//        response.setContentType("text/plain");
//        response.setCharacterEncoding("UTF-8");
        String nomeFile = "calibrazione_" + DateUtil.SDF2SIMPLEUSA.print(data.getStartDate().getTime()) + "_.dat";
        data.setResponseDataFile(csv);
        data.setResponseFileName(nomeFile);
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("data", data);
        return jsonResponse;
    }

}
