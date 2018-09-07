/**
 * 
 */
package net.wedjaa.wetnet.business.services;

import net.wedjaa.wetnet.web.spring.model.WetnetInfo;

/**
 * @author Massimo Ricci
 * @date Feb 10, 2015
 */
public interface InfoService {

    /**
     * 
     * @return le informazioni su WetNet
     */
    public WetnetInfo getWetnetInfo();
    
}
