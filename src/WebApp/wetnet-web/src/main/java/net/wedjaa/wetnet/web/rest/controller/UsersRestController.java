package net.wedjaa.wetnet.web.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.dao.UsersDAO;
import net.wedjaa.wetnet.business.domain.JSONResponse;
import net.wedjaa.wetnet.business.domain.Users;
import net.wedjaa.wetnet.business.domain.UsersHasDistricts;
import net.wedjaa.wetnet.security.UserDetailsImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
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
@RequestMapping("/users")
public class UsersRestController {
    
    @Autowired
    private MessageSource messages;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

    /**
     * 
     * @param response
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = { RequestMethod.GET })
    public List<Users> getUsers(HttpServletResponse response) {
        List<Users> result = usersDAO.getAll();
        return result;
    }

    /**
     * 
     * @param response
     * @param userId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "{id}", method = { RequestMethod.GET })
    public Users getUser(@PathVariable("id") long userId) {
        Users result = usersDAO.getById(userId);
        return result;
    }
    
    /**
     * 
     * @param authentication
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/current", method = { RequestMethod.GET })
    public Users getCurrentUser(Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());;
        return details.getUsers();
    }

    /**
     * 
     * @param request
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST })
    public JSONResponse saveUser(HttpServletRequest request, @RequestBody Users user) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        //se un nuovo inserimento endoding pwd
        if(user.getIdusers() == 0 && StringUtils.isNotBlank(user.getPassword())){
            user.setPassword(messageDigestPasswordEncoder.encodePassword(user.getPassword(), null));
        }
        jsonResponse.addData("user", usersDAO.saveOrUpdate(user));
        jsonResponse.setMessage(messages.getMessage("user.form.save.alert.success", null, request.getLocale()));
        return jsonResponse;
    }
    
    /**
     * 
     * @param request
     * @param password
     * @param authentication
     * 
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/passwordModify", method = { RequestMethod.PUT, RequestMethod.POST })
    public JSONResponse updatePassword(HttpServletRequest request, @RequestBody String newPassword, Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        Users userFromDb = usersDAO.getById(details.getIdusers());
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        userFromDb.setPassword(messageDigestPasswordEncoder.encodePassword(newPassword, null));
        jsonResponse.addData("user", usersDAO.saveOrUpdate(userFromDb));
        jsonResponse.setMessage(messages.getMessage("passwordModify.form.modify.alert.success", null, request.getLocale()));
        return jsonResponse;
    }
    
    /**
     * Verifica che la passord passata si uguale alla password dell'utente corrente
     * 
     * @param request
     * @param password
     * @param authentication
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/checkPassword", method = { RequestMethod.PUT, RequestMethod.POST })
    public JSONResponse checkPassword(HttpServletRequest request, @RequestBody String password, Authentication authentication) {
        UserDetailsImpl details =  ((UserDetailsImpl) authentication.getPrincipal());
        Users userFromDb = usersDAO.getById(details.getIdusers());
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        if(messageDigestPasswordEncoder.isPasswordValid(userFromDb.getPassword(), password, null)){
            jsonResponse.setStatus(JSONResponse.SUCCESS);
            jsonResponse.setMessage(messages.getMessage("passwordModify.form.checkPassword.alert.success", null, request.getLocale()));
        }
        else{
            jsonResponse.setStatus(JSONResponse.FAIL);
            jsonResponse.setMessage(messages.getMessage("passwordModify.form.checkPassword.alert.fail", null, request.getLocale()));
        }
        
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "{id}", method = { RequestMethod.DELETE })
    public JSONResponse deleteUser(HttpServletRequest request, @PathVariable("id") long userId) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.setMessage(messages.getMessage("user.form.delete.alert.success", null, request.getLocale()));
        usersDAO.delete(userId);
        return jsonResponse;
    }

    /**
     * 
     * @param request
     * @param usersid
     * @param districts_id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/districts", method = { RequestMethod.POST })
    public JSONResponse addDistricts(HttpServletRequest request, @RequestParam("usersid") long usersid, @RequestParam("districts_id") long districts_id) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        usersDAO.addDistrictsToUsers(usersid, districts_id);
        jsonResponse.setMessage(messages.getMessage("user.form.addDistricts.alert.success", null, request.getLocale()));
        return jsonResponse;
    }
    
    /**
     * 
     * @param request
     * @param usersid
     * @param districts_id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/districts", method = { RequestMethod.GET})
    public JSONResponse getDistricts(HttpServletRequest request, @RequestParam("usersid") long usersid) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        jsonResponse.addData("districts", usersDAO.getUsersHasDistrictsByUsersId(usersid));
        jsonResponse.setMessage(messages.getMessage("user.form.addDistricts.alert.success", null, request.getLocale()));
        return jsonResponse;
    }
    
    
    /**
     * 
     * @param request
     * @param usersid
     * @param districts_id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/districts", method = { RequestMethod.DELETE})
    public JSONResponse deleteDistricts(HttpServletRequest request, @RequestParam("usersid") long usersid, @RequestParam("districts_id") long districts_id) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        usersDAO.removeUsersHasDistricts(usersid, districts_id);
        jsonResponse.setMessage(messages.getMessage("user.form.addDistricts.alert.success", null, request.getLocale()));
        return jsonResponse;
    }
    
    
    /**
     * 
     * @param request
     * @param usersHasDistricts
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/districts", method = { RequestMethod.PUT })
    public JSONResponse updateDistricts(HttpServletRequest request, @RequestBody UsersHasDistricts usersHasDistricts) {
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.SUCCESS);
        usersDAO.updateUsersHasDistricts(usersHasDistricts);
        jsonResponse.setMessage(messages.getMessage("user.form.updateDistricts.alert.success", null, request.getLocale()));
        return jsonResponse;
    }
}
