package net.wedjaa.wetnet.web.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.dao.ConnectionsDAO;
import net.wedjaa.wetnet.business.domain.Connections;
import net.wedjaa.wetnet.business.domain.JSONResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author alessandro vincelli, massimo ricci
 *
 */
@Controller
@RequestMapping("/connections")
public class ConnectionsRestController {
    
    @Autowired
    private MessageSource messages;
    

    @Autowired
    private ConnectionsDAO connectionsDAO;

    /**
     * 
     * @param response
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = { RequestMethod.GET })
    public JSONResponse getAll(HttpServletRequest request) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("connections", connectionsDAO.getAll());
        return jsonResponse;
    }

    /**
     * 
     * @param response
     * @param id_odbcdsn
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "{id}", method = { RequestMethod.GET })
    public JSONResponse get(HttpServletResponse response, @PathVariable("id") long id_odbcdsn) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("connection", connectionsDAO.getById(id_odbcdsn));
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param connection
     * @return
     */
    //@Secured()
    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST })
    public JSONResponse save(HttpServletRequest request, @RequestBody Connections connection) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("connection", connectionsDAO.saveOrUpdate(connection));
        jsonResponse.setMessage(messages.getMessage("connection.form.save.alert.success", null, request.getLocale()));
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param id_odbcdsn
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "{id}", method = { RequestMethod.DELETE })
    public JSONResponse delete(HttpServletRequest request, @PathVariable("id") long id_odbcdsn) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.setMessage(messages.getMessage("connection.form.delete.alert.success", null, request.getLocale()));
        connectionsDAO.delete(id_odbcdsn);
        return jsonResponse;
    }

}
