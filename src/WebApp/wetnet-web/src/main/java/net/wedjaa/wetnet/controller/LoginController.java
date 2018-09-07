/**
 * 
 */
package net.wedjaa.wetnet.controller;

import java.security.Principal;
import java.util.logging.Logger;

import net.wedjaa.wetnet.business.dao.DistrictsDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author massimo ricci
 *
 */
@Controller
public class LoginController {

	protected static final Logger logger = Logger.getLogger("LoginController");

	@Autowired
	DistrictsDAO districtsService;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		logger.info("Received request '/welcome'");
		String name = principal!= null?principal.getName():"";
		model.addAttribute("username", name);
		return "wetnet/graphics/dashboard";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		logger.info("Received request '/login'");
		return "wetnet/login";
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		logger.info("Received request '/loginfailed'");
		model.addAttribute("error", "true");
		return "wetnet/login";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		logger.info("Received request '/logout'");
		return "wetnet/login";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String accessDeniedGet(ModelMap model, Principal principal) {
		logger.info("Received request '/denied'");
		String name = principal.getName();
		model.addAttribute("username", name);
		return "wetnet/denied";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/denied", method = RequestMethod.POST)
	public String accessDeniedPost(ModelMap model, Principal principal) {
		logger.info("Received request '/denied'");
		String name = principal.getName();
		model.addAttribute("username", name);
		return "wetnet/denied";
	}

	@RequestMapping(value = "/session-expired")
	public String sessionExpired(ModelMap model) {
		logger.info("Received request '/session-expired'");
		return "wetnet/session-expired";
	}
}
