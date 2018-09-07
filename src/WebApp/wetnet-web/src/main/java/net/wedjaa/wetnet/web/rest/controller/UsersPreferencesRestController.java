/**
 * 
 */
package net.wedjaa.wetnet.web.rest.controller;

import net.wedjaa.wetnet.business.domain.UsersPreferences;
import net.wedjaa.wetnet.util.UsersPreferencesHelper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author massimo ricci
 *
 */
@Controller
@RequestMapping("/preferences")
public class UsersPreferencesRestController {

    @RequestMapping(method = { RequestMethod.GET })
    public UsersPreferences getAllPrefs() {
        return UsersPreferencesHelper.getUsersPreferences();
    }
    
    @RequestMapping(method = { RequestMethod.POST })
    public UsersPreferences setAllPrefs(@RequestBody UsersPreferences usersPreferences) {       
        return UsersPreferencesHelper.setUsersPreferences(usersPreferences);
    }
}
