package net.wedjaa.wetnet.web.rest.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.commons.NumberUtil;
import net.wedjaa.wetnet.business.dao.DistrictsBandsHistoryDAO;
import net.wedjaa.wetnet.business.dao.DistrictsDAO;
import net.wedjaa.wetnet.business.domain.Districts;
import net.wedjaa.wetnet.business.domain.DistrictsBandsHistory;
import net.wedjaa.wetnet.business.domain.JSONResponse;
import net.wedjaa.wetnet.security.Roles;
import net.wedjaa.wetnet.security.UserDetailsImpl;

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

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
@Controller
@RequestMapping("/districtsBandsHistory")
public class DistrictsBandsHistoryRestController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private DistrictsDAO districtsDAO;
    
    @Autowired
    private DistrictsBandsHistoryDAO districtsBandsHistoryDAO;

    

    /**
     * 
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = { RequestMethod.GET })
    public JSONResponse getById(HttpServletResponse response, @PathVariable("id") long id) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        
        jsonResponse.addData("districtsBandsHistory", districtsDAO.getAllDistrictsBandsHistory(id));
        
        return jsonResponse;
    }

   
}
