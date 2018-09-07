package net.wedjaa.wetnet.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wedjaa.wetnet.business.domain.JSONResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Gestione Centralizzata eccezioni web, Viene restituito sempre un oggetto {@link JSONResponse}
 * 
 * @author alessandro vincelli
 *
 */
public class CustomExceptionResolver implements HandlerExceptionResolver, Ordered {

    @Autowired
    private MessageSource messages;
    private static final Logger logger = Logger.getLogger(CustomExceptionResolver.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("Unexpcted Error", ex);
        ModelAndView modelAndView = new ModelAndView();
        JSONResponse jsonResponse = new JSONResponse(JSONResponse.FAIL);
        jsonResponse.setMessage(messages.getMessage("error.message.generic", null, request.getLocale()));
        return modelAndView.addObject(jsonResponse);
    }

}
